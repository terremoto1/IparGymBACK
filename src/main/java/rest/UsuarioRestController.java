package com.ipargym.rest;

import com.ipargym.dtos.UsuarioAutenticarEntradaDto;
import com.ipargym.dtos.UsuarioAutenticarSalidaDto;
import com.ipargym.entidades.Usuario;
import com.ipargym.modelos.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UsuarioRepository usuarioRepository;
    private final String issuer;
    private final long caducidadSegundos;

    public UsuarioRestController(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            UsuarioRepository usuarioRepository,
            @Value("${seguridad.jwt.emisor:ipargym-api}") String issuer,
            @Value("${seguridad.jwt.caducidad:900}") long caducidadSegundos) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.usuarioRepository = usuarioRepository;
        this.issuer = issuer;
        this.caducidadSegundos = caducidadSegundos;
    }

    @PostMapping("/login")
    public UsuarioAutenticarSalidaDto autenticar(@Valid @RequestBody UsuarioAutenticarEntradaDto entrada) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                entrada.email(), entrada.password()));

        Usuario usuario = usuarioRepository.findByEmail(entrada.email())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado"));
        Instant ahora = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(ahora)
                .expiresAt(ahora.plusSeconds(caducidadSegundos))
                .subject(usuario.getEmail())
                .claim("nombre", usuario.getNombre())
                .claim("rol", usuario.getRol().name())
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        return new UsuarioAutenticarSalidaDto(token, "Bearer");
    }
}
