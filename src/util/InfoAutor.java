package util;

import java.lang.annotation.*;

// Anotação em tempo de execução
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InfoAutor {
    String nome();
    String data();
}