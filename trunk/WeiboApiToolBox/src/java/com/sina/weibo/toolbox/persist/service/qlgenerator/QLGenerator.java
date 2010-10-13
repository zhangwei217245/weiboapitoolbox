/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sina.weibo.toolbox.persist.service.qlgenerator;

/**
 *
 * @author x-spirit
 */
public interface QLGenerator {
    String FROM = "FROM";
    String GROUP_BY = "GROUP BY";
    String HAVING = "HAVING";
    String ORDER_BY = "ORDER BY";
    String SELECT = "SELECT";
    String WHERE = "WHERE";
    String IN = "IN";

    String getFrom_clause();

    String getGroupby_clause();

    String getHaving_clause();

    String getOrderby_clause();

    String getSelect_clause();

    String getWhere_clause();

    String getIn_clause(String id,String ...sps);

    void init();

    JPQLGenerator setFrom_clause(String from_clause);

    JPQLGenerator setGroupby_clause(String groupby_clause);

    JPQLGenerator setHaving_clause(String logical_operator, String having_clause);

    JPQLGenerator setOrderby_clause(String orderby_clause, String ascOrDesc);

    JPQLGenerator setSelect_clause(String select_clause);

    JPQLGenerator setWhere_clause(String logical_operator, String where_clause);

    @Override
    String toString();

}
