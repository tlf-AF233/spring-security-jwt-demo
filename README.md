## Spring-Security 为主的 RBAC 后台管理系统
### 无状态的 JWT 
> 本系统的授权采用 JWT 的方式。
> 在用户认证成功后，会返回一个 access_token 和 refresh_token，每次访问时需要带上 access_token，当 access_token 过期时，需要将 refresh_token 带上请求刷新令牌接口，然后得到新的 access_token 和 refresh_token。
