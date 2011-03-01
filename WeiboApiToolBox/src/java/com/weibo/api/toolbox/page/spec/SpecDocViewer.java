/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.page.spec;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.codemirror.Codemirror;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

/**
 *
 * @author x-spirit
 */
public class SpecDocViewer extends GenericForwardComposer {

    public static final String ARG_DOCPATH="docPath";
    public static final String ARG_SYNTAX="syntax";
    public static final String ARG_CODEVAL="codeValue";



    private static final long serialVersionUID = 2425352713897004641L;
    Codemirror code;
    Textbox textbox;

    public String read(String docpath) throws FileNotFoundException, IOException {
        java.io.Reader reader = new java.io.FileReader(docpath);
        String result = new String(org.zkoss.io.Files.readAll(reader));
        reader.close();
        return result;
    }

    public void setCode(String syntax,String docpath) {
         System.out.println(syntax+":"+docpath);
        try {
            String value = read(docpath);
            code.setSyntax(syntax);
            code.setValue(value);
            textbox.setValue(value);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpecDocViewer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SpecDocViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCodeString(String syntax,String codeString){
        System.out.println(syntax+":"+codeString);
        code.setSyntax(syntax);
        code.setValue(codeString);
        textbox.setValue(codeString);
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        Map argmap = Executions.getCurrent().getArg();
        System.out.println(argmap);
        super.doAfterCompose(comp);
        String syntax = argmap.get(ARG_SYNTAX)==null?"txt":argmap.get(ARG_SYNTAX)+"";
        if (argmap.get(ARG_CODEVAL)!=null){
            setCodeString(syntax, argmap.get(ARG_CODEVAL)+"");
        } else if (argmap.get(ARG_DOCPATH)!=null){
            setCode(syntax, argmap.get(ARG_DOCPATH)+"");
        }
    }
}
