package com.weibo.api.spec.wiki;

import com.weibo.api.toolbox.persist.entity.Tspeccategory;


/**
 *
 * @author x-spirit
 */
public class WikiGeneratorImpl implements WikiGenerator{

    
    public void generateSpecWikiByCate(Tspeccategory cate){
        if (cate.getNumparentcateid().getNumcateid()!=1){
            
        }
    }

    public void generateMenuByParentCate(Tspeccategory pcate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
