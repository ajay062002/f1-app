package com.example.f1app.controller.dto;

public class AuthResponse {
  public boolean ok;
  public String message;
  public String name;
  public String email;

  public static AuthResponse ok(String name, String email) {
    AuthResponse r = new AuthResponse();
    r.ok = true; r.message = "ok"; r.name = name; r.email = email;
    return r;
  }
  public static AuthResponse fail(String msg) {
    AuthResponse r = new AuthResponse();
    r.ok = false; r.message = msg;
    return r;
  }
}
