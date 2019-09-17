package local.tux.app.sso.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/users")
class UserController {

    @GetMapping("/")
    fun get(principal: Principal): ResponseEntity<Principal> {
        return ResponseEntity.ok(principal)
    }
}