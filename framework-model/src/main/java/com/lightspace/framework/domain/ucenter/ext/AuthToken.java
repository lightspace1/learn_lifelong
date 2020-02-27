package com.lightspace.framework.domain.ucenter.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class AuthToken {
  String access_token;
  String refresh_token;
  String jwt_token;
}
