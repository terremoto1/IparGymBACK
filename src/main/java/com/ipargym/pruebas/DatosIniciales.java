package com.ipargym.pruebas;

import com.ipargym.entidades.Actividad;
import com.ipargym.entidades.Rol;
import com.ipargym.entidades.Usuario;
import com.ipargym.modelos.ActividadRepository;
import com.ipargym.modelos.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/** Carga datos de demostración solo fuera del perfil de producción. */
@Component
@Profile("!prod")
public class DatosIniciales implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final ActividadRepository actividadRepository;
    private final PasswordEncoder passwordEncoder;

    public DatosIniciales(
            UsuarioRepository usuarioRepository,
            ActividadRepository actividadRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.actividadRepository = actividadRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        crearUsuarioSiNoExiste("Administradora", "admin@ipargym.es", "Admin123!", Rol.ADMINISTRADOR);
        crearUsuarioSiNoExiste("Ana Socia", "ana@ipargym.es", "Socia123!", Rol.SOCIO);
        crearUsuarioSiNoExiste("Luis Socio", "luis@ipargym.es", "Socia123!", Rol.SOCIO);
        crearActividadesSiVacio();
    }

    private void crearUsuarioSiNoExiste(String nombre, String email, String password, Rol rol) {
        if (!usuarioRepository.existsByEmail(email)) {
            usuarioRepository.save(new Usuario(nombre, email, passwordEncoder.encode(password), rol));
        }
    }

    private void crearActividadesSiVacio() {
        if (actividadRepository.count() > 0) {
            return;
        }

        LocalDateTime ahora = LocalDateTime.now();
        actividadRepository.save(new Actividad(
                "Spinning", "Ciclismo indoor con música", "Laura",
                ahora.plusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0), 45, 12));
        actividadRepository.save(new Actividad(
                "Yoga", "Movilidad y respiración", "Marta",
                ahora.plusDays(2).withHour(10).withMinute(0).withSecond(0).withNano(0), 60, 10));
        actividadRepository.save(new Actividad(
                "Pilates", "Fuerza y control postural", "Luis",
                ahora.plusDays(3).withHour(19).withMinute(0).withSecond(0).withNano(0), 50, 8));
        actividadRepository.save(new Actividad(
                "Crossfit", "Entrenamiento funcional", "Carlos",
                ahora.plusDays(4).withHour(18).withMinute(30).withSecond(0).withNano(0), 55, 1));
        actividadRepository.save(new Actividad(
                "Zumba", "Baile y cardio", "Elena",
                ahora.minusDays(2), 50, 15));
        actividadRepository.save(new Actividad(
                "Body Pump", "Fuerza con barra", "Sergio",
                ahora.plusDays(5).withHour(17).withMinute(0).withSecond(0).withNano(0), 45, 15));
    }
}
