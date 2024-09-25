package ru.exdata.moex;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "moex-iss",
                version = "0.1",
                description = "My API",
//                license = @License(name = "Apache 2.0", url = "https://foo.bar"),
                contact = @Contact(url = "https://t.me/leovante", name = "Dmitry", email = "leovante@live.ru")
        ),
        servers = @Server(url = "http://localhost:${APP_PORT}")
)
public class MicronautApp {
    public static void main(String[] args) {
        Micronaut.build(args)
                .eagerInitSingletons(true)
                .mainClass(MicronautApp.class)
                .start();
    }
}
