package se.nt1c.authservice.service


interface TokenService {
    fun validate(): Boolean
}
