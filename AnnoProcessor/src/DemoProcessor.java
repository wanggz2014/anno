import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

/**
 * *********************** 版权声明 ***********************************
 *
 * 版权所有：wgz
 * ©CopyRight wgz03310126 2014   
 *  
 * *******************************************************************
 */

/**
 * 关于注解处理器的demo
 *
 * @author    wgz
 * @version   Ver 3.0
 * @since     Ver 3.0
 * @Date      2014-8-26
 *
 */
@SupportedAnnotationTypes(value={"*"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class DemoProcessor extends AbstractProcessor {
    
    //文件创建接口
    private Filer filer;
    
    //信息发送接口
    private Messager message;
    
    @Override
    public void init(ProcessingEnvironment env) {
        super.init(env);
        filer = env.getFiler();
        message = env.getMessager();
    }
    

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        String simpleName=null;
        for(Element element : roundEnv.getRootElements()){
            
            simpleName=element.getSimpleName().toString();
            
            if(simpleName.startsWith("Demo")){
                //对于以demo开头的命名不进行处理
                continue;
            }
            
            if(simpleName.startsWith("Test")){ 
                this.message.printMessage(Kind.WARNING, "存在以test开头的类名");
                String newName="Demo".concat(simpleName.substring(4));
                String newContext="public class "+newName +"{"+
                                  " public "+newName+"(){System.out.println(\"success\");}"+
                                  "}";
                                  
                JavaFileObject file=null;
                try{
                    file=filer.createSourceFile(newName, element);
                    file.openWriter().append(newContext).close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        
        return true;
        
    }
    
}

