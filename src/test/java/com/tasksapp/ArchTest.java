package com.tasksapp;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.tasksapp");

        noClasses()
            .that()
                .resideInAnyPackage("com.tasksapp.service..")
            .or()
                .resideInAnyPackage("com.tasksapp.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.tasksapp.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
