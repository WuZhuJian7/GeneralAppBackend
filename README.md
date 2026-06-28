# General App Backend

基于 Spring Boot 3.x 的 App 后端脚手架，开箱即用。

## 技术栈

| 组件 | 版本 | 用途 |
|------|------|------|
| Spring Boot | 3.2.x | 基础框架 |
| Spring Security | 6.2.x | 认证（JWT） |
| MyBatis-Plus | 3.5.x | ORM |
| MySQL | 8.0 | 数据库 |
| Redis | 7.x | 缓存 + Token存储 |
| Hutool | 5.8.x | 工具库 |
| JJWT | 0.12.x | JWT生成/解析 |
| Springdoc | 2.5.x | 接口文档（Swagger） |
| Lombok | - | 代码简化 |

## 快速开始

### 1. 环境准备

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.x+

### 2. 初始化数据库

```bash
mysql -u root -p < sql/init.sql
```

### 3. 修改配置

编辑 `src/main/resources/application-dev.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/general_app?...
    username: your_username
    password: your_password
  
  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password

jwt:
  secret: your-256-bit-secret-key-here
```

### 4. 启动项目

```bash
mvn spring-boot:run
```

### 5. 访问接口文档

启动后访问：http://localhost:8080/api/swagger-ui.html

## 项目结构

```
src/main/java/com/general/app/
├── GeneralAppBackendApplication.java    # 启动类
├── common/
│   ├── constant/
│   │   └── ResultCode.java              # 统一状态码
│   ├── exception/
│   │   ├── BusinessException.java        # 业务异常
│   │   └── GlobalExceptionHandler.java   # 全局异常处理
│   └── result/
│       ├── PageResult.java               # 分页结果
│       └── Result.java                   # 统一响应
├── config/
│   ├── MybatisPlusConfig.java            # MyBatis-Plus配置
│   ├── MyMetaObjectHandler.java          # 自动填充
│   ├── RedisConfig.java                  # Redis配置
│   ├── SwaggerConfig.java                # Swagger配置
│   └── WebConfig.java                    # 跨域配置
├── security/
│   ├── JwtAuthenticationFilter.java      # JWT过滤器
│   ├── JwtUtil.java                      # JWT工具类
│   ├── LoginUser.java                    # 登录用户
│   ├── SecurityConfig.java               # Security配置
│   ├── SecurityUtil.java                 # Security工具
│   └── UserDetailsServiceImpl.java       # 用户认证
├── util/
│   └── RedisUtil.java                    # Redis工具类
└── module/
    └── user/                             # 用户模块（示例）
        ├── controller/
        │   ├── AuthController.java       # 认证接口
        │   └── UserController.java       # 用户接口
        ├── entity/
        │   ├── User.java                 # 用户实体
        │   ├── dto/UserDTO.java          # 数据传输对象
        │   ├── query/UserQuery.java      # 查询条件
        │   └── vo/UserVO.java            # 视图对象
        ├── mapper/
        │   └── UserMapper.java           # Mapper接口
        └── service/
            ├── UserService.java          # Service接口
            └── impl/UserServiceImpl.java # Service实现
```

## 框架能力

| 能力 | 状态 | 说明 |
|------|------|------|
| 统一响应 | ✅ | Result + ResultCode |
| 全局异常 | ✅ | GlobalExceptionHandler |
| JWT认证 | ✅ | JwtUtil + SecurityConfig |
| 接口文档 | ✅ | Springdoc OpenAPI |
| MyBatis-Plus | ✅ | 分页 + 逻辑删除 + 乐观锁 |
| Redis | ✅ | JSON序列化策略 |
| 参数校验 | ✅ | @Valid |
| 日志 | ✅ | Logback |
| 跨域 | ✅ | WebConfig |
| 操作日志 | 🔲 | 有需要可加 |
| 限流 | 🔲 | 有需要可加 |

## 接口说明

### 认证接口

```http
# 登录
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}

# 登出
POST /api/auth/logout
Authorization: Bearer {token}
```

### 用户接口

```http
# 注册用户
POST /api/user/register
Content-Type: application/json

{
  "username": "test",
  "password": "123456",
  "nickname": "测试用户"
}

# 分页查询
GET /api/user/page?pageNum=1&pageSize=10
Authorization: Bearer {token}

# 获取详情
GET /api/user/{id}
Authorization: Bearer {token}

# 更新用户
PUT /api/user
Authorization: Bearer {token}
Content-Type: application/json

{
  "id": 1,
  "nickname": "新昵称"
}

# 删除用户
DELETE /api/user/{id}
Authorization: Bearer {token}
```

## 后续开发流程

1. 复制这个脚手架
2. 建业务表
3. 写 Entity
4. 写 Mapper
5. 写 Service
6. 写 Controller

## License

MIT
