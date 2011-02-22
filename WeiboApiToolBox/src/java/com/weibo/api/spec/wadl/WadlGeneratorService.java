package com.weibo.api.spec.wadl;

import com.weibo.api.toolbox.persist.entity.Tspec;
import java.util.List;

/**
 *
 * @author x-spirit
 */
public interface WadlGeneratorService {

    public void generateSingleWadl(Tspec tspec);

    public void generateWholeWadl(List<Tspec> speclist);

    public void generateEachWadl(List<Tspec> speclist);
    
}
