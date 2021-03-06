<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>1.5.3.RELEASE</version>
    </parent>
  <groupId>${basePackageName}</groupId>
  <artifactId>${projectName}-parent</artifactId>
  <packaging>pom</packaging>
  <version>1.0.0-SNAPSHOT</version>
  <name>${r"$"}{project.artifactId} v${r"$"}{project.version}</name>

  <modules>
    <module>${projectName}-core</module>
    <module>${projectName}-service</module>
  </modules>

  <build>
    <resources>
      <resource>
        <directory>src/main/resources</directory>
      </resource>
    </resources>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
        </configuration>
      </plugin>
    </plugins>
  </build>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>${r"$"}{project.groupId}</groupId>
        <artifactId>${projectName}-core</artifactId>
        <version>${r"$"}{project.version}</version>
      </dependency>
      <dependency>
        <groupId>${r"$"}{project.groupId}</groupId>
        <artifactId>${projectName}-service</artifactId>
        <version>${r"$"}{project.version}</version>
      </dependency>
    </dependencies>
  </dependencyManagement>
</project>
