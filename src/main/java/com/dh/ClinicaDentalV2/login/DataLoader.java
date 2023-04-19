package com.dh.ClinicaDentalV2.login;

import com.dh.ClinicaDentalV2.login.entity.Rol;
import com.dh.ClinicaDentalV2.login.entity.Usuario;
import com.dh.ClinicaDentalV2.login.repository.UsuarioRepository;
import com.dh.ClinicaDentalV2.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UsuarioRepository repository;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordAdmin = passwordEncoder.encode("admin");
        String passwordUser = passwordEncoder.encode("user");

        Optional<Usuario> admin = repository.findByUsername("admin");
        if (admin.isEmpty()) {
            repository.save(new Usuario("admin", "admin@dh.com", "admin", passwordAdmin, Rol.ADMIN));
            LOGGER.info("Se ha creado el usuario por defecto 'admin' ");
        }

        Optional<Usuario> user = repository.findByUsername("user");
        if (user.isEmpty()) {
            repository.save(new Usuario("user", "user@dh.com", "user", passwordUser, Rol.USER));
            LOGGER.info("Se ha creado el usuario por defecto 'user' ");
        }
    }
}
