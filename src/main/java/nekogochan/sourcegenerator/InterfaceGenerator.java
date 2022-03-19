package nekogochan.sourcegenerator;

public class InterfaceGenerator extends Generator {
  public InterfaceGenerator(String $package, String $name) {
    super($package, "public interface %s".formatted($name));
  }

  public void addField(String field) {
    add(1, field);
  }

  public void addMethod(String signature) {
    add(1, signature + ";");
  }

  public void addDefaultMethod(String signature, String body) {
    super.addMethod("default " + signature, body);
  }

  public void addStaticMethod(String signature, String body) {
    super.addMethod("static " + signature, body);
  }
}