/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.spec.wadl;

import com.weibo.api.spec.wadl.wadl20090202.Application;
import com.weibo.api.spec.wadl.wadl20090202.Method;
import com.weibo.api.spec.wadl.wadl20090202.Representation;
import com.weibo.api.spec.wadl.wadl20090202.Request;
import com.weibo.api.spec.wadl.wadl20090202.Resource;
import com.weibo.api.spec.wadl.wadl20090202.Resources;
import com.weibo.api.spec.wadl.wadl20090202.Response;
import com.weibo.api.toolbox.persist.entity.Terrorcode;
import com.weibo.api.toolbox.persist.entity.Tresponse;
import com.weibo.api.toolbox.persist.entity.Tspec;
import java.util.List;

/**
 *
 * @author x-spirit
 */
public interface WadlBinding {

    Application bindApplication(List<Tspec> specList);

    void bindErrorRepresentation(Response resp, Terrorcode errcode);

    void bindErrorResponse(Method mtd, Tspec spec);

    void bindGrammers(Application appdefine);

    /**
     * bind spec subresource to its last Resource node.
     * @param res
     * @param spec
     */
    void bindLastResource(Resource res, Tspec spec);

    /**
     * bind Method node to its last Resource node
     * according to the
     * @param rs
     * @param spec
     */
    void bindMethod(Resource rs, Tspec spec);

    void bindRepresentationParameters(Representation rep, Tspec spec);

    void bindRequest(Method mtd, Tspec spec);

    void bindRequestParameters(Request req, Tspec spec);

    void bindRequestRepresentation(Request req, Tspec spec);

    /**
     * bind spec main resource to its first Resource node
     * if the spec doesn't have a subresource,
     * bind the main resource to its last Resource node.
     * @param res
     * @param spec
     */
    void bindResource(Resource res, Tspec spec);

    /**
     * bind Resource node for each spec
     * @param ress
     * @param sameBaseSepcs
     */
    void bindResources(Resources ress, List<Tspec> sameBaseSepcs);

    void bindResourcesByBaseUrl(Application appdefine, List<Tspec> specList);

    void bindResponse(Method mtd, Tspec spec);

    void bindResponseRepresentation(Response resp, Tresponse tresp);

    void bindTemplateAndMatrixParam(Resource res, Tspec spec);

}
