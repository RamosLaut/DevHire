package TP_Final.devhire.Controllers;

import TP_Final.devhire.Services.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }
    @Operation(
            summary = "Obtener estadísticas generales",
            description = "Permite a un usuario con rol ROLE_ADMIN ver las estadísticas generales del sistema.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado. Solo ROLE_ADMIN puede acceder a este recurso")
    })
    @GetMapping
    public ResponseEntity<String> getStats(){
        return ResponseEntity.ok(statsService.getStats());
    }
}
