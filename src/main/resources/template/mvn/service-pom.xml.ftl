<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>${basePackageName}.${projectName}</groupId>
    <artifactId>${projectName}-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
  </parent>
  <artifactId>${projectName}-service</artifactId>
  <packaging>war</packaging>
  <name>${r"$"}{project.artifactId} v${r"$"}{project.version}</name>

  <build>
    <finalName>${projectName}-service</finalName>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
      <plugin>
        <artifactId>maven-assembly-plugin</artifactId>
        <configuration>
          <appendAssemblyId>false</appendAssemblyId>
          <descriptors>
            <descriptor>assembly.xml</descriptor>
          </descriptors>
        </configuration>
        <executions>
          <execution>
            <id>build</id>
            <phase>package</phase>
            <goals>
              <goal>single</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>

  <dependencies>
    <dependency>
      <groupId>${r"$"}{project.groupId}</groupId>
      <artifactId>${projectName}-core</artifactId>
    </dependency>
  </dependencies>
</project>
