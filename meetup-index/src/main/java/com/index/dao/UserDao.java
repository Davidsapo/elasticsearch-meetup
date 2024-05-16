package com.index.dao;

import com.index.entity.User;
import com.index.enums.UserSort;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.search.sort.ScriptSortBuilder.ScriptSortType.NUMBER;

/**
 * User Dao.
 *
 * @author David Sapozhnik
 */
@Repository
public class UserDao {

    public static final String USER_ID_FIELD = "entity_id";
    public static final String FULL_NAME_FIELD = "full_name";
    public static final String EMAIL_FIELD = "email";
    public static final String PHONE_FIELD = "phone";
    public static final String ORDERS_FIELD = "orders";
    public static final String ORDERS_STATUS_FIELD = "status";
    public static final String PAID_ORDER_STATUS = "PAID";
    public static final String AMOUNT_PAID_FIELD = "amount_paid";

    private static final String TOTAL_AMOUNT_PAID_SCRIPT = "params['_source']['" + ORDERS_FIELD + "'].stream()" +
            ".filter(p -> p['" + ORDERS_STATUS_FIELD + "'] == '" + PAID_ORDER_STATUS + "')" +
            ".mapToDouble(p -> p['" + AMOUNT_PAID_FIELD + "'])" +
            ".sum()";

    @Autowired
    private ElasticsearchRestTemplate template;

    /**
     * Save user.
     *
     * @param user user entity
     * @return entity.
     */
    public User save(User user) {
        return template.save(user);
    }

    /**
     * Find by entity id.
     *
     * @param entityId entity id
     * @return {@link Optional} of generic object
     */
    public Optional<User> findById(UUID entityId) {
        var query = new NativeSearchQuery(QueryBuilders.matchQuery(USER_ID_FIELD, resolveUUID(entityId)));
        return template.search(query, User.class)
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .findFirst();
    }

    /**
     * Find all by search list.
     *
     * @param search the search
     * @return the list
     */
    public List<User> findAllBySearch(String search, UserSort sort, SortOrder direction) {

        var query = new NativeSearchQueryBuilder();

        if (nonNull(search)) {
            query.withQuery(QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchPhrasePrefixQuery(FULL_NAME_FIELD, search))
                    .should(QueryBuilders.prefixQuery(EMAIL_FIELD, search))
                    .should(QueryBuilders.prefixQuery(PHONE_FIELD, search)));
        }

        if (nonNull(sort)) {
            switch (sort) {
                case EMAIL -> query.withSort(SortBuilders.fieldSort(EMAIL_FIELD).order(direction));
                case AMOUNT_PAID ->
                        query.withSort(SortBuilders.scriptSort(new Script(TOTAL_AMOUNT_PAID_SCRIPT), NUMBER).order(direction));
            }
        }

        return template.search(query.build(), User.class)
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .collect(toList());
    }

    /* Helpers */

    private String resolveUUID(UUID uuid) {
        return uuid.toString();
    }

    private Collection<String> resolveUUIDs(Collection<UUID> uuids) {
        return uuids.stream().map(this::resolveUUID).collect(toList());
    }
}