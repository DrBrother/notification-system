package ru.tinkoff;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

public class JooqCodegen {
    public static void main(String[] args) throws Exception {
        Database database = new Database()
                .withName("org.jooq.meta.extensions.liquibase.LiquibaseDatabase")
                .withProperties(
                        new Property().withKey("rootPath").withValue("migrations"),
                        new Property().withKey("scripts").withValue("master.yaml")
                );

        Generate options = new Generate()
                .withGeneratedAnnotation(true)
                .withGeneratedAnnotationDate(false)
                .withNullableAnnotation(true)
                .withNullableAnnotationType("org.jetbrains.annotations.Nullable")
                .withNonnullAnnotation(true)
                .withNonnullAnnotationType("org.jetbrains.annotations.NotNull")
                .withJpaAnnotations(false)
                .withValidationAnnotations(true)
                .withSpringAnnotations(true)
                .withConstructorPropertiesAnnotation(true)
                .withConstructorPropertiesAnnotationOnPojos(true)
                .withConstructorPropertiesAnnotationOnRecords(true)
                .withFluentSetters(false)
                .withDaos(false)
                .withPojos(false);

        Target target = new Target()
                .withPackageName("ru.tinkoff.edu.java.scrapper.entity.jooq")
                .withDirectory("scrapper/src/main/java");

        Configuration configuration = new Configuration()
                .withGenerator(
                        new Generator()
                                .withDatabase(database)
                                .withGenerate(options)
                                .withTarget(target)
                                .withStrategy(new Strategy()
                                        .withName("org.jooq.codegen.DefaultGeneratorStrategy")
                                )
                );

        GenerationTool.generate(configuration);
    }
}