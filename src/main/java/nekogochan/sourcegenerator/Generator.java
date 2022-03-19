package nekogochan.sourcegenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.StringJoiner;

public abstract class Generator {

  protected static final int BASE_OFFSET = 2;

  protected final String $package;
  protected final StringJoiner classCode;

  protected Generator(String $package, String $signature) {
    this.$package = $package;
    this.classCode = new StringJoiner(
      "\n",
      """
        package %s;
              
        %s {
        """.formatted($package, $signature),
      "}"
    );
  }

  public String get() {
    return classCode.toString();
  }

  public void writeLocal() throws IOException {
    write(Path.of("src/main/java/" + $package));
  }

  public void write(Path path) throws IOException {
    Files.writeString(
      path,
      this.get(),
      StandardOpenOption.WRITE
    );
  }

  protected void addField(String field) {
    add(1, field);
  }

  protected void addMethod(String signature, String body) {
    add(1, signature + " {");
    Arrays.stream(body.split("\n"))
          .forEach(row -> add(2, row));
    add(1, "}");
  }

  protected void add(int offset, String code) {
    classCode.add(offset(offset) + code);
  }

  protected static String offset(int depth) {
    var chars = new char[depth * BASE_OFFSET];
    Arrays.fill(chars, ' ');
    return String.valueOf(chars);
  }
}