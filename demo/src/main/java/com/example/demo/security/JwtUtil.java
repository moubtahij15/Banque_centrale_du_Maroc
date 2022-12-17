package com.example.demo.security;

public class JwtUtil {
    public static final String SECRET = "secret";
    public static final String AUTH_HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final long EXPIRE_ACCESS_TOKEN = 100*60 * 1000;
    public static final long EXPIRE_ACCESS_refresh = 2 * 60 * 1000;


}