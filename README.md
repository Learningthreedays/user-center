# 用户中心系统

## 项目介绍
这是一个基于 Spring Boot 和 MyBatis-Plus 开发的用户中心系统，提供用户注册、登录、管理等基础功能。系统采用了 RESTful 风格的 API 设计，并实现了基础的权限控制。

## 技术栈
- **后端框架：** Spring Boot 2.6.13
- **ORM 框架：** MyBatis-Plus 3.5.3.1
- **数据库：** MySQL
- **工具类库：** 
  - Lombok：简化对象封装
  - commons-lang3：提供常用工具类
  - knife4j：接口文档生成

## 功能特性
### 用户管理
- [x] 用户注册
  - 账号长度 ≥ 6
  - 密码长度 ≥ 8
  - 账号不能重复
  - 学号不能重复
  - 密码加密存储
- [x] 用户登录
  - 账号密码验证
  - 登录态管理
- [x] 用户注销
- [x] 获取当前登录用户信息
- [x] 用户搜索（仅管理员）
- [x] 用户删除（仅管理员）

### 系统特性
- [x] 自定义异常处理
- [x] 全局统一返回
- [x] 分层架构设计
- [x] 数据库字段自动填充
- [x] 用户信息脱敏处理
- [x] 逻辑删除支持

## 快速开始

### 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+

### 安装步骤
1. 克隆项目到本地
```bash
git clone [项目地址]
```

2. 创建数据库
```sql
create database center;
```

3. 修改配置文件
修改 `src/main/resources/application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/center?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2b8
    username: your_username
    password: your_password
```

4. 编译运行
```bash
mvn spring-boot:run
```

### 接口说明
| 接口路径 | 方法 | 描述 | 权限要求 |
|---------|------|------|----------|
| /user/register | POST | 用户注册 | 无 |
| /user/login | POST | 用户登录 | 无 |
| /user/logout | POST | 用户注销 | 登录用户 |
| /user/current | GET | 获取当前用户信息 | 登录用户 |
| /user/search | GET | 搜索用户 | 管理员 |
| /user/delete | POST | 删除用户 | 管理员 |

## 项目结构
```
user-center
├── src/main/java
│   └── edu.hpu.usercenter
│       ├── common          // 通用类
│       ├── contant        // 常量定义
│       ├── controller     // 控制器层
│       ├── exception      // 异常处理
│       ├── handler        // 处理器
│       ├── mapper         // 数据访问层
│       ├── model          // 数据模型
│       ├── service        // 业务逻辑层
│       └── UserCenterApplication.java
├── resources
│   ├── application.yml    // 配置文件
│   └── mapper            // MyBatis XML映射文件
└── pom.xml
```

## 开发规范
1. 代码规范遵循阿里巴巴Java开发规范
2. 接口遵循 RESTful 设计规范
3. 注释完整，包含方法说明、参数说明、返回值说明
4. 异常处理统一使用自定义业务异常
5. 返回结果统一使用统一响应对象包装

## 注意事项
1. 密码传输建议使用 HTTPS
2. 生产环境部署前请修改默认密码盐值
3. 建议定期备份数据库
4. 注意防止 SQL 注入等安全问题

## 作者
- 作者：向优秀

## 版权说明
该项目使用 MIT 许可证，详情请参阅 LICENSE 文件。
