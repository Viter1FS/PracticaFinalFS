package com.example.vitech_FS.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration, lo que indica que es una clase de configuración y la utilizaremos para añadir CORS para nuestra aplicacion
// se implementa WebMvcConfigurer que permite la configuracion de Spring MVC
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configura CORS para todas las rutas de la aplicación (/** significa cualquier ruta)
        registry.addMapping("/**")
                /** si se esta desarrollando con Vite el servidor de desarrollo de Vite
                *  se ejecutará en http://localhost:5173 de forma predeterminada por eso utilizamos solicitudes de este origen
                */
                .allowedOrigins("http://localhost:5173")
                // Permite los métodos HTTP GET, POST, PUT y DELETE
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                //Permite cualquier encabezado en la solicitud CORS
                .allowedHeaders("*")
                //Permite el envío de cookies y credenciales en las solicitudes CORS
                .allowCredentials(true);
    }
}
