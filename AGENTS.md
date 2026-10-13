# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Implementación de Manejo de Errores y Reintentos en un Sistema de Procesamiento de Pagos**.

| | |
|---|---|
| Tema | E2E prod technologiesWork 202610131810 |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean con manejo reactivo de errores y reintentos |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-webflux 3.5.6
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-actuator n/a
- io.github.resilience4j:resilience4j-spring-boot3 2.2.0
- org.postgresql:postgresql n/a
- org.projectlombok:lombok n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.junit.jupiter:junit-jupiter-api n/a
- org.mockito:mockito-core n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Configuración del Canal de Entrada**: Servicio que acepta y persiste solicitudes de pago con idempotencia.
- **Fase 2 — Consolidación de Canales y Manejo de Errores**: Sistema que consolida los tres canales de entrada y maneja errores de forma efectiva.
- **Fase 3 — Recuperación Automática y Emisión de Eventos**: Sistema con recuperación automática y emisión de eventos de auditoría.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/pragma/payment/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (60)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/payment/Application.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Hooks pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/Application.java` — `reactor.util.retry`
      El import reactor.util.retry.Retry pertenece a reactor.util.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/domain/port/ExternalServicePort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/domain/port/AuditEventPort.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/repository/PaymentRepository.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/ExternalServiceAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/ExternalServiceAdapter.java` — `reactor.util.retry`
      El import reactor.util.retry.Retry pertenece a reactor.util.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/AuditEventAdapter.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/infrastructure/config/GlobalExceptionHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `PaymentRequest.idempotencyKey`
      Se invoca `idempotencyKey` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `ExternalServicePort.verifyFunds`
      Se invoca `verifyFunds` sobre `ExternalServicePort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `PaymentRequest.accountId`
      Se invoca `accountId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `PaymentRequest.id`
      Se invoca `id` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/application/PaymentService.java` — `AuditEventPort.emitPaymentFailedEvent`
      Se invoca `emitPaymentFailedEvent` sobre `AuditEventPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.findById`
      Se invoca `findById` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.save`
      Se invoca `save` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setStatus`
      Se invoca `setStatus` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentStatus.name`
      Se invoca `name` sobre `PaymentStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.existsByIdempotencyKey`
      Se invoca `existsByIdempotencyKey` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setTransactionId`
      Se invoca `setTransactionId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.transactionId`
      Se invoca `transactionId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setChannel`
      Se invoca `setChannel` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.channel`
      Se invoca `channel` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setAmount`
      Se invoca `setAmount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setIdempotencyKey`
      Se invoca `setIdempotencyKey` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.idempotencyKey`
      Se invoca `idempotencyKey` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setAccountId`
      Se invoca `setAccountId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.accountId`
      Se invoca `accountId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.status`
      Se invoca `status` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setCreatedAt`
      Se invoca `setCreatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.createdAt`
      Se invoca `createdAt` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setUpdatedAt`
      Se invoca `setUpdatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.updatedAt`
      Se invoca `updatedAt` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getId`
      Se invoca `getId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getTransactionId`
      Se invoca `getTransactionId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getChannel`
      Se invoca `getChannel` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getAmount`
      Se invoca `getAmount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getAccountId`
      Se invoca `getAccountId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getIdempotencyKey`
      Se invoca `getIdempotencyKey` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getStatus`
      Se invoca `getStatus` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getCreatedAt`
      Se invoca `getCreatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getUpdatedAt`
      Se invoca `getUpdatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `PaymentRequestDTO.channel`
      Se invoca `channel` sobre `PaymentRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `PaymentRequestDTO.transactionId`
      Se invoca `transactionId` sobre `PaymentRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `PaymentRequestDTO.amount`
      Se invoca `amount` sobre `PaymentRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `PaymentRequestDTO.idempotencyKey`
      Se invoca `idempotencyKey` sobre `PaymentRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.transactionId`
      Se invoca `transactionId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.channel`
      Se invoca `channel` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.idempotencyKey`
      Se invoca `idempotencyKey` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.status`
      Se invoca `status` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — `ExternalServicePort.verifyFunds`
      Se invoca `verifyFunds` sobre `ExternalServicePort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — `AuditEventPort.publish`
      Se invoca `publish` sobre `AuditEventPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (21)

- `pom.xml`
- `src/main/java/com/pragma/payment/Application.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/payment/domain/model/PaymentRequest.java`
- `src/main/java/com/pragma/payment/domain/model/PaymentResponse.java`
- `src/main/java/com/pragma/payment/domain/exception/DuplicatePaymentException.java`
- `src/main/java/com/pragma/payment/domain/exception/ExternalServiceException.java`
- `src/main/java/com/pragma/payment/domain/exception/NetworkException.java`
- `src/main/java/com/pragma/payment/domain/port/PaymentPersistencePort.java`
- `src/main/java/com/pragma/payment/domain/port/ExternalServicePort.java`
- `src/main/java/com/pragma/payment/domain/port/AuditEventPort.java`
- `src/main/java/com/pragma/payment/infrastructure/repository/PaymentRepository.java`
- `src/main/java/com/pragma/payment/application/PaymentService.java`
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java`
- `src/main/java/com/pragma/payment/infrastructure/adapter/ExternalServiceAdapter.java`
- `src/main/java/com/pragma/payment/infrastructure/adapter/AuditEventAdapter.java`
- `src/main/java/com/pragma/payment/infrastructure/config/Resilience4jConfig.java`
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java`
- `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java`
- `src/main/java/com/pragma/payment/infrastructure/config/GlobalExceptionHandler.java`
- `src/test/java/com/pragma/payment/application/PaymentServiceTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/payment`
- `src/main/java/com/pragma/payment/application`
- `src/main/java/com/pragma/payment/domain`
- `src/main/java/com/pragma/payment/domain/exception`
- `src/main/java/com/pragma/payment/domain/model`
- `src/main/java/com/pragma/payment/domain/port`
- `src/main/java/com/pragma/payment/infrastructure`
- `src/main/java/com/pragma/payment/infrastructure/adapter`
- `src/main/java/com/pragma/payment/infrastructure/config`
- `src/main/java/com/pragma/payment/infrastructure/controller`
- `src/main/java/com/pragma/payment/infrastructure/repository`
- `src/test/java/com/pragma/payment`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con manejo reactivo de errores y reintentos**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2
- Brecha que el reto ataca: Comprueba manejo de errores y reintentos.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
