/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weibo.api.spec.wadl;

import com.weibo.api.spec.wadl.wadl20061109.Application;
import com.weibo.api.spec.wadl.wadl20061109.Resource;
import com.weibo.api.spec.wadl.wadl20061109.Resources;
import com.weibo.api.toolbox.persist.entity.Tspec;
import java.util.List;

/**
 *
 * @author x-spirit
 */
public class Wadl0902GeneratorImpl extends AbstractWadlGenerator {

    public void generateSingleWadl(Tspec tspec) {
        Application app = new Application();
        Resources ress = new Resources();
        Resource res = new Resource();
        if (tspec.getVc2subresource() != null && tspec.getVc2subresource().length() > 0) {
            Resource baseRes = generateBaseResource(tspec);
            res.setPath(tspec.getVc2mainresource());
            res.getMethodOrResource().add(baseRes);
        } else {
            res.setId(tspec.getVc2specname());
            res.setPath(tspec.getVc2mainresource());
            res.setQueryType(tspec.getEnumAcceptType().getMediaString());
        }
        ress.getResource().add(res);
        app.setResources(ress);
    }

    private Resource generateBaseResource(Tspec spec) {
        Resource res = new Resource();
        res.setPath(spec.getVc2subresource());
        res.setQueryType(spec.getEnumAcceptType().getMediaString());

        return res;
    }

    public void generateWholeWadl(List<Tspec> speclist) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void generateEachWadl(List<Tspec> speclist) {
        if (speclist != null && speclist.size() > 0) {
            for (Tspec spec : speclist) {
                generateSingleWadl(spec);
            }
        }
    }
}
