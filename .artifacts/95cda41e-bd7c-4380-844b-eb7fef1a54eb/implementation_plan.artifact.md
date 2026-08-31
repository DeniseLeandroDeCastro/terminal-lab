# Corrigir Configuração do Room e TransactionEntity

A entidade `TransactionEntity` e a configuração do Room no projeto apresentam problemas de importação e dependências incorretas. O `room-compiler` está sendo usado indevidamente como `implementation` e faltam as bibliotecas de runtime.

## User Review Required

> [!IMPORTANT]
> Vou configurar o plugin **KSP (Kotlin Symbol Processing)** para o Room, que é a recomendação atual do Google em substituição ao Kapt. Isso exige adicionar o plugin no arquivo de build.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///home/denise/StudioProjects/CieloPayLab/gradle/libs.versions.toml)
- Adicionar versões para Room Runtime e KSP plugin.
- Adicionar definições para `androidx-room-runtime`, `androidx-room-ktx` e o plugin KSP.

#### [MODIFY] [build.gradle.kts (App)](file:///home/denise/StudioProjects/CieloPayLab/app/build.gradle.kts)
- Aplicar o plugin KSP.
- Corrigir as dependências do Room: usar `implementation` para runtime/ktx e `ksp` para o compilador.

### Data Layer

#### [MODIFY] [TransactionEntity.kt](file:///home/denise/StudioProjects/CieloPayLab/app/src/main/java/br/com/denisecastro/cielopaylab/data/local/TransactionEntity.kt)
- Garantir que as anotações `@Entity` e `@PrimaryKey` estejam importadas corretamente de `androidx.room`.

## Verification Plan

### Automated Tests
- Executar `./gradlew assembleDebug` para verificar se o processamento de símbolos do Room funciona e o código compila.
