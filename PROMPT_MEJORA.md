# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/pragma/payment/infrastructure/config/Resilience4jConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.
- `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/payment/Application.java` — `reactor.core.publisher`: El import reactor.core.publisher.Hooks pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/Application.java` — `reactor.util.retry`: El import reactor.util.retry.Retry pertenece a reactor.util.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/domain/port/ExternalServicePort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/domain/port/AuditEventPort.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/repository/PaymentRepository.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/adapter/ExternalServiceAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/adapter/ExternalServiceAdapter.java` — `reactor.util.retry`: El import reactor.util.retry.Retry pertenece a reactor.util.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/adapter/AuditEventAdapter.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/infrastructure/config/GlobalExceptionHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `PaymentRequest.idempotencyKey`: Se invoca `idempotencyKey` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `ExternalServicePort.verifyFunds`: Se invoca `verifyFunds` sobre `ExternalServicePort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `PaymentRequest.accountId`: Se invoca `accountId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `PaymentRequest.id`: Se invoca `id` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/application/PaymentService.java` — `AuditEventPort.emitPaymentFailedEvent`: Se invoca `emitPaymentFailedEvent` sobre `AuditEventPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.findById`: Se invoca `findById` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.save`: Se invoca `save` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setStatus`: Se invoca `setStatus` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentStatus.name`: Se invoca `name` sobre `PaymentStatus`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRepository.existsByIdempotencyKey`: Se invoca `existsByIdempotencyKey` sobre `PaymentRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setTransactionId`: Se invoca `setTransactionId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.transactionId`: Se invoca `transactionId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setChannel`: Se invoca `setChannel` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.channel`: Se invoca `channel` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setAmount`: Se invoca `setAmount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setIdempotencyKey`: Se invoca `setIdempotencyKey` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.idempotencyKey`: Se invoca `idempotencyKey` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setAccountId`: Se invoca `setAccountId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.accountId`: Se invoca `accountId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.status`: Se invoca `status` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setCreatedAt`: Se invoca `setCreatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.createdAt`: Se invoca `createdAt` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.setUpdatedAt`: Se invoca `setUpdatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentRequest.updatedAt`: Se invoca `updatedAt` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getId`: Se invoca `getId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getTransactionId`: Se invoca `getTransactionId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getChannel`: Se invoca `getChannel` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getAmount`: Se invoca `getAmount` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getAccountId`: Se invoca `getAccountId` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getIdempotencyKey`: Se invoca `getIdempotencyKey` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getStatus`: Se invoca `getStatus` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getCreatedAt`: Se invoca `getCreatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java` — `PaymentEntity.getUpdatedAt`: Se invoca `getUpdatedAt` sobre `PaymentEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `PaymentRequestDTO.channel`: Se invoca `channel` sobre `PaymentRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `PaymentRequestDTO.transactionId`: Se invoca `transactionId` sobre `PaymentRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `PaymentRequestDTO.amount`: Se invoca `amount` sobre `PaymentRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java` — `PaymentRequestDTO.idempotencyKey`: Se invoca `idempotencyKey` sobre `PaymentRequestDTO`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.transactionId`: Se invoca `transactionId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.channel`: Se invoca `channel` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.idempotencyKey`: Se invoca `idempotencyKey` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java` — `PaymentRequest.status`: Se invoca `status` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — `ExternalServicePort.verifyFunds`: Se invoca `verifyFunds` sobre `ExternalServicePort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/payment/application/PaymentServiceTest.java` — `AuditEventPort.publish`: Se invoca `publish` sobre `AuditEventPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2

### Brecha de conocimiento
Comprueba manejo de errores y reintentos.

### Reto
- Tema: E2E prod technologiesWork 202610131810
- Seniority: senior-l2
- Tipo: practical
- Título: Implementación de Manejo de Errores y Reintentos en un Sistema de Procesamiento de Pagos
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Configuración del Canal de Entrada — objetivo: Establecer un canal de entrada operativo que persista las solicitudes de pago con idempotencia. — entregable (NO resolver): Servicio que acepta y persiste solicitudes de pago con idempotencia.
- Fase 2: Consolidación de Canales y Manejo de Errores — objetivo: Consolidar los tres canales de entrada sin pérdida de datos ante backpressure de uno de ellos y manejar adecuadamente los errores. — entregable (NO resolver): Sistema que consolida los tres canales de entrada y maneja errores de forma efectiva.
- Fase 3: Recuperación Automática y Emisión de Eventos — objetivo: Implementar la recuperación automática ante caída del consumidor downstream y emitir evento al sistema de auditoría por cada aceptación. — entregable (NO resolver): Sistema con recuperación automática y emisión de eventos de auditoría.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>payment-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>payment-service</name>
    <description>Servicio de procesamiento de pagos con manejo reactivo de errores y reintentos</description>

    <properties>
        <java.version>21</java.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Base de datos -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/pragma/payment/Application.java ===
package com.pragma.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Hooks;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.beans.factory.annotation.Autowired;
import com.pragma.payment.domain.port.PaymentPersistencePort;
import com.pragma.payment.domain.port.ExternalServicePort;
import com.pragma.payment.domain.port.AuditEventPort;
import com.pragma.payment.application.PaymentService;
import com.pragma.payment.infrastructure.adapter.PaymentJpaAdapter;
import com.pragma.payment.infrastructure.adapter.ExternalServiceAdapter;
import com.pragma.payment.infrastructure.adapter.AuditEventAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.function.Function;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(basePackages = "com.pragma.payment.infrastructure.repository")
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.pragma.payment"})
public class Application {

    @Autowired
    private PaymentPersistencePort paymentPersistencePort;

    @Autowired
    private ExternalServicePort externalServicePort;

    @Autowired
    private AuditEventPort auditEventPort;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("externalService");
        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> {
                    if (event.getStateTransition() == CircuitBreaker.StateTransition.FORCED_OPEN_TO_CLOSED) {
                        System.out.println("Circuit breaker recuperado, volviendo a operar");
                    }
                });
    }

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(paymentPersistencePort, externalServicePort, auditEventPort);
    }

    @Bean
    public Function<Throwable, Throwable> retryOnSpecificExceptions() {
        return throwable -> {
            if (throwable instanceof ExternalServiceException ||
                throwable instanceof NetworkException) {
                return throwable;
            }
            return new RuntimeException("Error no recuperable", throwable);
        };
    }

    @Bean
    public PaymentJpaAdapter paymentJpaAdapter() {
        return new PaymentJpaAdapter();
    }

    @Bean
    public ExternalServiceAdapter externalServiceAdapter() {
        return new ExternalServiceAdapter();
    }

    @Bean
    public AuditEventAdapter auditEventAdapter() {
        return new AuditEventAdapter();
    }
}

import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.NetworkException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;

class PaymentServiceInitializer {

    public static Mono<Void> initializePaymentFlow(PaymentService paymentService) {
        return Mono.fromRunnable(() -> {
            System.out.println("Inicializando flujo de pagos con manejo reactivo");
        }).then(Mono.defer(() -> paymentService.processPayment(
            new PaymentRequest("123e4567-e89b-12d3-a456-426614174000",
                              "web",
                              100.0,
                              "idempotency-key-123")))
        ).retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
            .filter(throwable -> throwable instanceof ExternalServiceException ||
                                throwable instanceof NetworkException)
            .doAfterRetry(signal -> System.out.println("Reintentando pago tras error: " + signal.failure().getMessage()))
        ).onErrorResume(ex -> {
            System.err.println("Error irrecuperable en el procesamiento del pago: " + ex.getMessage());
            return Mono.empty();
        });
    }

    record PaymentRequest(String transactionId, String channel, double amount, String idempotencyKey) {}
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: payment-service
  profiles:
    active: dev
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/paymentdb
    username: paymentuser
    password: paymentpass
    pool:
      enabled: true
      initial-size: 5
      max-size: 10
      max-idle-time: 30m
  datasource:
    url: jdbc:postgresql://localhost:5432/paymentdb
    username: paymentuser
    password: paymentpass
    driver-class-name: org.postgresql.Driver
    hikari:
      connection-timeout: 20000
      maximum-pool-size: 5
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        jdbc:
          lob:
            non_contextual_creation: true

server:
  port: 8080
  error:
    include-message: always
    include-binding-errors: always

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,circuitbreakers
  endpoint:
    health:
      show-details: always
  health:
    circuitbreakers:
      enabled: true

resilience4j:
  circuitbreaker:
    instances:
      externalService:
        registerHealthIndicator: true
        failureRateThreshold: 50
        minimumNumberOfCalls: 5
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 10s
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowSize: 10
        slidingWindowType: COUNT_BASED
        recordExceptions:
          - com.pragma.payment.domain.exception.ExternalServiceException
          - com.pragma.payment.domain.exception.NetworkException
        ignoreExceptions:
          - java.lang.IllegalStateException
  retry:
    instances:
      externalService:
        maxAttempts: 3
        waitDuration: 1s
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        retryExceptions:
          - com.pragma.payment.domain.exception.ExternalServiceException
          - com.pragma.payment.domain.exception.NetworkException
        ignoreExceptions:
          - java.lang.IllegalStateException
  timelimiter:
    instances:
      externalService:
        timeoutDuration: 2s

logging:
  level:
    com.pragma.payment: DEBUG
    org.springframework.web: INFO
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
    io.github.resilience4j: DEBUG

// === ARCHIVO: src/main/java/com/pragma/payment/domain/model/PaymentRequest.java ===
package com.pragma.payment.domain.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modelo de dominio que representa una solicitud de pago.
 * Contiene la información necesaria para procesar un pago, incluyendo
 * la clave de idempotencia para garantizar que no se procese más de una vez.
 */
public record PaymentRequest(
    @NotBlank(message = "El ID de transacción no puede estar vacío")
    @Size(max = 64, message = "El ID de transacción no puede exceder 64 caracteres")
    String transactionId,

    @NotBlank(message = "El canal no puede estar vacío")
    @Pattern(regexp = "^(web|mobile|api)$", message = "El canal debe ser web, mobile o api")
    String channel,

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    BigDecimal amount,

    @NotBlank(message = "La clave de idempotencia no puede estar vacía")
    @Size(max = 128, message = "La clave de idempotencia no puede exceder 128 caracteres")
    String idempotencyKey,

    @NotNull(message = "El timestamp no puede ser nulo")
    LocalDateTime timestamp,

    PaymentStatus status
) {
    /**
     * Constructor compacto para crear una solicitud de pago con estado inicial PENDING.
     */
    public PaymentRequest {
        Objects.requireNonNull(transactionId, "transactionId cannot be null");
        Objects.requireNonNull(channel, "channel cannot be null");
        Objects.requireNonNull(amount, "amount cannot be null");
        Objects.requireNonNull(idempotencyKey, "idempotencyKey cannot be null");
        Objects.requireNonNull(timestamp, "timestamp cannot be null");

        if (status == null) {
            status = PaymentStatus.PENDING;
        }
    }

    /**
     * Enum que representa los posibles estados de una solicitud de pago.
     */
    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        DUPLICATE
    }

    /**
     * Valida que la solicitud de pago cumpla con las reglas de negocio.
     * @throws IllegalArgumentException si alguna validación falla
     */
    public void validate() {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (!channel.matches("^(web|mobile|api)$")) {
            throw new IllegalArgumentException("Canal inválido. Debe ser web, mobile o api");
        }
    }

    /**
     * Crea una copia de la solicitud de pago con un nuevo estado.
     * @param newStatus el nuevo estado
     * @return una nueva instancia de PaymentRequest con el estado actualizado
     */
    public PaymentRequest withStatus(PaymentStatus newStatus) {
        return new PaymentRequest(
            this.transactionId,
            this.channel,
            this.amount,
            this.idempotencyKey,
            this.timestamp,
            newStatus
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/model/PaymentResponse.java ===
package com.pragma.payment.domain.model;


import com.pragma.payment.PaymentRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modelo de dominio que representa la respuesta de una solicitud de pago.
 * Contiene información sobre el resultado del procesamiento del pago.
 */
public record PaymentResponse(
    String paymentId,
    PaymentRequest.PaymentStatus status,
    LocalDateTime timestamp,
    String message
) {
    /**
     * Constructor compacto para crear una respuesta de pago.
     */
    public PaymentResponse {
        Objects.requireNonNull(paymentId, "paymentId cannot be null");
        Objects.requireNonNull(status, "status cannot be null");
        Objects.requireNonNull(timestamp, "timestamp cannot be null");

        if (message == null) {
            message = "";
        }
    }

    /**
     * Crea una respuesta exitosa para un pago completado.
     * @param paymentId el ID del pago generado
     * @return una instancia de PaymentResponse con estado COMPLETED
     */
    public static PaymentResponse success(String paymentId) {
        return new PaymentResponse(
            paymentId,
            PaymentRequest.PaymentStatus.COMPLETED,
            LocalDateTime.now(),
            "Pago procesado exitosamente"
        );
    }

    /**
     * Crea una respuesta para un pago fallido.
     * @param paymentId el ID del pago (puede ser null si no se persistió)
     * @param errorMessage mensaje de error descriptivo
     * @return una instancia de PaymentResponse con estado FAILED
     */
    public static PaymentResponse failure(String paymentId, String errorMessage) {
        return new PaymentResponse(
            paymentId != null ? paymentId : "",
            PaymentRequest.PaymentStatus.FAILED,
            LocalDateTime.now(),
            errorMessage
        );
    }

    /**
     * Crea una respuesta para un pago duplicado.
     * @param paymentId el ID del pago original que ya fue procesado
     * @return una instancia de PaymentResponse con estado DUPLICATE
     */
    public static PaymentResponse duplicate(String paymentId) {
        return new PaymentResponse(
            paymentId,
            PaymentRequest.PaymentStatus.DUPLICATE,
            LocalDateTime.now(),
            "Pago duplicado detectado"
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/exception/DuplicatePaymentException.java ===
package com.pragma.payment.domain.exception;

import java.io.Serial;

/**
 * Excepción lanzada cuando se detecta un intento de procesar un pago duplicado.
 * Esto ocurre cuando se recibe una solicitud con la misma clave de idempotencia
 * para la cual ya existe un pago procesado en el sistema.
 */
public class DuplicatePaymentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String idempotencyKey;
    private final String existingPaymentId;

    /**
     * Constructor para la excepción de pago duplicado.
     * @param idempotencyKey la clave de idempotencia duplicada
     * @param existingPaymentId el ID del pago existente que tiene la misma clave
     * @param message mensaje descriptivo del error
     */
    public DuplicatePaymentException(String idempotencyKey, String existingPaymentId, String message) {
        super(message);
        this.idempotencyKey = idempotencyKey;
        this.existingPaymentId = existingPaymentId;
    }

    /**
     * Obtiene la clave de idempotencia que causó el conflicto.
     * @return la clave de idempotencia
     */
    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    /**
     * Obtiene el ID del pago existente que tiene la misma clave de idempotencia.
     * @return el ID del pago existente
     */
    public String getExistingPaymentId() {
        return existingPaymentId;
    }

    /**
     * Crea un mensaje detallado para esta excepción.
     * @param idempotencyKey la clave de idempotencia duplicada
     * @param existingPaymentId el ID del pago existente
     * @return mensaje detallado del error
     */
    public static String createMessage(String idempotencyKey, String existingPaymentId) {
        return String.format(
            "Pago con clave de idempotencia '%s' ya existe como pago '%s'",
            idempotencyKey,
            existingPaymentId
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/exception/ExternalServiceException.java ===
package com.pragma.payment.domain.exception;

import java.io.Serial;

public class ExternalServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String serviceName;
    private final String errorCode;
    private final boolean retryable;

    public ExternalServiceException(String serviceName, String errorCode, String message) {
        super(message);
        this.serviceName = serviceName;
        this.errorCode = errorCode;
        this.retryable = determineRetryable(errorCode);
    }

    public ExternalServiceException(String serviceName, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.serviceName = serviceName;
        this.errorCode = errorCode;
        this.retryable = determineRetryable(errorCode);
    }

    private static boolean determineRetryable(String errorCode) {
        if (errorCode == null) {
            return false;
        }
        return errorCode.startsWith("ERR_TIMEOUT") || 
               errorCode.startsWith("ERR_CONNECTION") ||
               errorCode.equals("ERR_SERVICE_UNAVAILABLE");
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public static String createMessage(String serviceName, String errorCode, String details) {
        return String.format("External service error [%s]: %s - %s", serviceName, errorCode, details);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/exception/NetworkException.java ===
package com.pragma.payment.domain.exception;

import java.io.Serial;
import java.net.URI;

public class NetworkException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String host;
    private final int port;
    private final int httpStatusCode;
    private final long timeoutMillis;
    private final boolean retryable;

    public NetworkException(String host, int port, String message) {
        super(message);
        this.host = host;
        this.port = port;
        this.httpStatusCode = 0;
        this.timeoutMillis = 0L;
        this.retryable = true;
    }

    public NetworkException(String host, int port, int httpStatusCode, String message) {
        super(message);
        this.host = host;
        this.port = port;
        this.httpStatusCode = httpStatusCode;
        this.timeoutMillis = 0L;
        this.retryable = isHttpStatusRetryable(httpStatusCode);
    }

    public NetworkException(String host, int port, long timeoutMillis, String message) {
        super(message);
        this.host = host;
        this.port = port;
        this.httpStatusCode = 0;
        this.timeoutMillis = timeoutMillis;
        this.retryable = true;
    }

    public NetworkException(String host, int port, String message, Throwable cause) {
        super(message, cause);
        this.host = host;
        this.port = port;
        this.httpStatusCode = 0;
        this.timeoutMillis = 0L;
        this.retryable = true;
    }

    public NetworkException(URI uri, String message, Throwable cause) {
        super(message, cause);
        this.host = uri.getHost();
        this.port = uri.getPort();
        this.httpStatusCode = 0;
        this.timeoutMillis = 0L;
        this.retryable = true;
    }

    private static boolean isHttpStatusRetryable(int statusCode) {
        return statusCode == 408 || statusCode == 429 || statusCode >= 500;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public static String createConnectionMessage(String host, int port) {
        return String.format("Connection failed to %s:%d", host, port);
    }

    public static String createTimeoutMessage(String host, int port, long timeoutMillis) {
        return String.format("Connection timeout to %s:%d after %dms", host, port, timeoutMillis);
    }

    public static String createHttpErrorMessage(String host, int port, int statusCode) {
        return String.format("HTTP error %d from %s:%d", statusCode, host, port);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/port/PaymentPersistencePort.java ===
package com.pragma.payment.domain.port;

import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentRequest.PaymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentPersistencePort {

    PaymentRequest save(PaymentRequest paymentRequest);

    Optional<PaymentRequest> findById(UUID paymentId);

    Optional<PaymentRequest> findByIdempotencyKey(String idempotencyKey);

    PaymentRequest updateStatus(UUID paymentId, PaymentStatus newStatus);

    List<PaymentRequest> findByStatus(PaymentStatus status);

    List<PaymentRequest> findByChannel(String channel);

    List<PaymentRequest> findByChannelAndStatus(String channel, PaymentStatus status);

    boolean existsByIdempotencyKey(String idempotencyKey);

    long countByStatus(PaymentStatus status);

    List<PaymentRequest> findPendingPaymentsOlderThan(long timestampMillis);
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/port/ExternalServicePort.java ===
package com.pragma.payment.domain.port;

import com.pragma.payment.domain.model.PaymentRequest;
import reactor.core.publisher.Mono;

public interface ExternalServicePort {

    Mono<Boolean> verifyFundsAvailability(PaymentRequest paymentRequest);

    Mono<Boolean> validateClientAccount(String clientId);

    Mono<String> getServiceHealthStatus();
}

// === ARCHIVO: src/main/java/com/pragma/payment/domain/port/AuditEventPort.java ===
package com.pragma.payment.domain.port;

import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentRequest.PaymentStatus;
import reactor.core.publisher.Mono;

public interface AuditEventPort {

    Mono<Void> emitPaymentAcceptedEvent(PaymentRequest paymentRequest);

    Mono<Void> emitPaymentRejectedEvent(PaymentRequest paymentRequest, String rejectionReason);

    Mono<Void> emitPaymentStatusChangedEvent(PaymentRequest paymentRequest, PaymentStatus oldStatus, PaymentStatus newStatus);

    Mono<Void> emitPaymentProcessedEvent(PaymentRequest paymentRequest, String externalReference);
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/repository/PaymentRepository.java ===
package com.pragma.payment.infrastructure.repository;

import com.pragma.payment.infrastructure.repository.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.idempotencyKey = :idempotencyKey")
    Mono<Optional<PaymentEntity>> findByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey);

    @Query("SELECT p FROM PaymentEntity p WHERE p.transactionId = :transactionId")
    Mono<Optional<PaymentEntity>> findByTransactionId(@Param("transactionId") String transactionId);

    @Query("SELECT p FROM PaymentEntity p WHERE p.status = :status")
    Mono<java.util.List<PaymentEntity>> findByStatus(@Param("status") String status);

    @Query("SELECT p FROM PaymentEntity p WHERE p.clientId = :clientId ORDER BY p.createdAt DESC")
    Mono<java.util.List<PaymentEntity>> findByClientIdOrderByCreatedAtDesc(@Param("clientId") String clientId);

    @Query("SELECT COUNT(p) FROM PaymentEntity p WHERE p.status = :status AND p.createdAt >= :since")
    Mono<Long> countByStatusAndCreatedAtSince(@Param("status") String status, @Param("since") java.time.Instant since);

    @Query("SELECT p FROM PaymentEntity p WHERE p.status = 'PENDING' AND p.createdAt < :timeout")
    Mono<java.util.List<PaymentEntity>> findTimedOutPendingPayments(@Param("timeout") java.time.Instant timeout);
}

// === ARCHIVO: src/main/java/com/pragma/payment/application/PaymentService.java ===
package com.pragma.payment.application;


import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.DuplicatePaymentException;
import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentRequest.PaymentStatus;
import com.pragma.payment.domain.model.PaymentResponse;
import com.pragma.payment.domain.port.AuditEventPort;
import com.pragma.payment.domain.port.ExternalServicePort;
import com.pragma.payment.domain.port.PaymentPersistencePort;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Duration RETRY_BACKOFF = Duration.ofMillis(500);

    private final PaymentPersistencePort paymentPersistencePort;
    private final ExternalServicePort externalServicePort;
    private final AuditEventPort auditEventPort;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public Mono<PaymentResponse> processPayment(PaymentRequest request) {
        log.info("Iniciando procesamiento de pago con idempotencyKey: {}", request.idempotencyKey());
        
        return validateAndPersistPayment(request)
                .flatMap(this::verifyFundsWithExternalService)
                .flatMap(this::updatePaymentStatus)
                .flatMap(this::emitAuditEvent)
                .onErrorResume(handleError(request));
    }

    private Mono<PaymentRequest> validateAndPersistPayment(PaymentRequest request) {
        return Mono.fromCallable(() -> {
            request.validate();
            return request;
        })
        .flatMap(validRequest -> paymentPersistencePort.findByIdempotencyKey(validRequest.idempotencyKey())
                .flatMap(existing -> Mono.error(
                        new DuplicatePaymentException(
                                request.idempotencyKey(),
                                existing.id(),
                                DuplicatePaymentException.createMessage(request.idempotencyKey(), existing.id())
                        )
                ))
                .switchIfEmpty(Mono.defer(() -> {
                    PaymentRequest pendingRequest = request.withStatus(PaymentStatus.PENDING);
                    return paymentPersistencePort.save(pendingRequest);
                }))
        );
    }

    private Mono<PaymentRequest> verifyFundsWithExternalService(PaymentRequest request) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("externalService");
        
        return externalServicePort.verifyFunds(request.accountId(), request.amount())
                .timeout(Duration.ofSeconds(5))
                .transformDeferred(mono -> CircuitBreakerOperator.of(circuitBreaker, mono))
                .flatMap(response -> {
                    if (!response.fundsAvailable()) {
                        return Mono.error(new ExternalServiceException("Fondos insuficientes"));
                    }
                    return Mono.just(request);
                });
    }

    private Mono<PaymentRequest> updatePaymentStatus(PaymentRequest request) {
        PaymentRequest approvedRequest = request.withStatus(PaymentStatus.APPROVED);
        return paymentPersistencePort.save(approvedRequest)
                .doOnSuccess(saved -> log.info("Pago aprobado con ID: {}", saved.id()));
    }

    private Mono<PaymentResponse> emitAuditEvent(PaymentRequest request) {
        return auditEventPort.emitPaymentProcessedEvent(request)
                .thenReturn(PaymentResponse.success(request.id()))
                .doOnSuccess(response -> log.info("Evento de auditoría emitido para pago: {}", request.id()));
    }

    private Function<Throwable, Mono<? extends PaymentResponse>> handleError(PaymentRequest request) {
        return throwable -> {
            log.error("Error procesando pago con idempotencyKey: {} - Tipo: {}", 
                    request.idempotencyKey(), throwable.getClass().getSimpleName(), throwable);
            
            if (throwable instanceof DuplicatePaymentException dupEx) {
                return paymentPersistencePort.findByIdempotencyKey(dupEx.getIdempotencyKey())
                        .map(existing -> PaymentResponse.duplicate(existing.id()));
            }
            
            return persistErrorAndReturnResponse(request, throwable);
        };
    }

    private Mono<PaymentResponse> persistErrorAndReturnResponse(PaymentRequest request, Throwable throwable) {
        PaymentRequest failedRequest = request.withStatus(PaymentStatus.FAILED);
        
        return paymentPersistencePort.save(failedRequest)
                .flatMap(saved -> auditEventPort.emitPaymentFailedEvent(saved, throwable.getMessage())
                        .thenReturn(PaymentResponse.failure(saved.id(), throwable.getMessage())));
    }

    public Mono<PaymentResponse> retryPayment(String paymentId) {
        return paymentPersistencePort.findById(paymentId)
                .filter(p -> p.status() == PaymentStatus.FAILED)
                .flatMap(this::processPayment)
                .switchIfEmpty(Mono.error(new IllegalStateException("Pago no encontrado o no está en estado FAILED")));
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/adapter/PaymentJpaAdapter.java ===
package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentRequest.PaymentStatus;
import com.pragma.payment.domain.port.PaymentPersistencePort;
import com.pragma.payment.infrastructure.repository.PaymentRepository;
import com.pragma.payment.infrastructure.repository.entity.PaymentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentJpaAdapter implements PaymentPersistencePort {

    private final PaymentRepository paymentRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Mono<PaymentRequest> findByIdempotencyKey(String idempotencyKey) {
        return Mono.fromCallable(() -> paymentRepository.findByIdempotencyKey(idempotencyKey)
                        .map(this::toDomain))
                .flatMap(Mono::justOrEmpty);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<PaymentRequest> findById(String paymentId) {
        return Mono.fromCallable(() -> paymentRepository.findById(paymentId)
                        .map(this::toDomain))
                .flatMap(Mono::justOrEmpty);
    }

    @Override
    @Transactional
    public Mono<PaymentRequest> save(PaymentRequest paymentRequest) {
        return Mono.fromCallable(() -> {
            PaymentEntity entity = toEntity(paymentRequest);
            PaymentEntity savedEntity = paymentRepository.save(entity);
            entityManager.flush();
            return toDomain(savedEntity);
        });
    }

    @Override
    @Transactional
    public Mono<Void> updateStatus(String paymentId, PaymentStatus status) {
        return Mono.fromRunnable(() -> paymentRepository.findById(paymentId)
                .ifPresent(entity -> {
                    entity.setStatus(status.name());
                    paymentRepository.save(entity);
                    entityManager.flush();
                }));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Boolean> existsByIdempotencyKey(String idempotencyKey) {
        return Mono.fromCallable(() -> paymentRepository.existsByIdempotencyKey(idempotencyKey));
    }

    private PaymentEntity toEntity(PaymentRequest request) {
        PaymentEntity entity = new PaymentRequest();
        entity.setTransactionId(request.transactionId());
        entity.setChannel(request.channel());
        entity.setAmount(request.amount());
        entity.setIdempotencyKey(request.idempotencyKey());
        entity.setAccountId(request.accountId());
        entity.setStatus(request.status() != null ? request.status().name() : PaymentStatus.PENDING.name());
        entity.setCreatedAt(request.createdAt());
        entity.setUpdatedAt(request.updatedAt());
        return entity;
    }

    private PaymentRequest toDomain(PaymentEntity entity) {
        return new PaymentRequest(
                entity.getId(),
                entity.getTransactionId(),
                entity.getChannel(),
                entity.getAmount(),
                entity.getAccountId(),
                entity.getIdempotencyKey(),
                PaymentStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/adapter/ExternalServiceAdapter.java ===
package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.NetworkException;
import com.pragma.payment.domain.port.ExternalServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@Slf4j
public class ExternalServiceAdapter implements ExternalServicePort {

    private final WebClient webClient;
    private final String externalServiceUrl;
    private final int connectionTimeout;
    private final int readTimeout;

    public ExternalServiceAdapter(
            @Value("${external.service.url:https://api.external-payment-provider.com}") String externalServiceUrl,
            @Value("${external.service.timeout.connection:3000}") int connectionTimeout,
            @Value("${external.service.timeout.read:5000}") int readTimeout) {
        this.externalServiceUrl = externalServiceUrl;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
        this.webClient = WebClient.builder()
                .baseUrl(externalServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Override
    public Mono<FundsVerificationResponse> verifyFunds(String accountId, double amount) {
        log.info("Verificando fondos para cuenta: {} por monto: {}", accountId, amount);
        
        String requestBody = String.format("{\"accountId\":\"%s\",\"amount\":%.2f}", accountId, amount);
        
        return webClient.post()
                .uri("/v1/accounts/{accountId}/verify-funds", accountId)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(FundsVerificationResponse.class)
                .timeout(Duration.ofMillis(readTimeout))
                .doOnSuccess(response -> log.info("Verificación de fondos exitosa para cuenta: {}", accountId))
                .doOnError(error -> log.error("Error verificando fondos para cuenta {}: {}", accountId, error.getMessage()))
                .retryWhen(Retry.backoff(3, Duration.ofMillis(500))
                        .filter(throwable -> throwable instanceof WebClientResponseException.ServiceUnavailable
                                || throwable instanceof WebClientResponseException.GatewayTimeout)
                        .doBeforeRetry(signal -> log.warn("Reintentando verificación de fondos, intento: {}", signal.totalRetries() + 1)))
                .onErrorResume(WebClientResponseException.ServiceUnavailable.class, e -> {
                    log.error("Servicio externo no disponible: {}", e.getMessage());
                    return Mono.error(new ExternalServiceException("Servicio de verificación de fondos no disponible"));
                })
                .onErrorResume(WebClientResponseException.GatewayTimeout.class, e -> {
                    log.error("Timeout del servicio externo: {}", e.getMessage());
                    return Mono.error(new NetworkException("Timeout al verificar fondos"));
                })
                .onErrorResume(WebClientResponseException.BadRequest.class, e -> {
                    log.error("Solicitud inválida al servicio externo: {}", e.getMessage());
                    return Mono.error(new ExternalServiceException("Solicitud de verificación inválida"));
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error HTTP del servicio externo: {} - {}", e.getStatusCode(), e.getMessage());
                    return Mono.error(new ExternalServiceException("Error comunicando con servicio de fondos: " + e.getStatusCode()));
                })
                .onErrorResume(Exception.class, e -> {
                    log.error("Error inesperado al verificar fondos: {}", e.getMessage());
                    return Mono.error(new NetworkException("Error de conexión: " + e.getMessage()));
                });
    }

    @Override
    public Mono<Boolean> checkServiceHealth() {
        return webClient.get()
                .uri("/health")
                .retrieve()
                .bodyToMono(HealthCheckResponse.class)
                .map(response -> "UP".equals(response.status()))
                .onErrorReturn(false);
    }

    public record FundsVerificationResponse(boolean fundsAvailable, String accountId, double availableBalance) {
        public static FundsVerificationResponse insufficientFunds(String accountId) {
            return new FundsVerificationResponse(false, accountId, 0.0);
        }

        public static FundsVerificationResponse sufficientFunds(String accountId, double balance) {
            return new FundsVerificationResponse(true, accountId, balance);
        }
    }

    public record HealthCheckResponse(String status, String version) {}
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/adapter/AuditEventAdapter.java ===
package com.pragma.payment.infrastructure.adapter;

import com.pragma.payment.domain.port.AuditEventPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditEventAdapter implements AuditEventPort {

    private static final String EVENT_TYPE_PAYMENT_CREATED = "PAYMENT_CREATED";
    private static final String EVENT_TYPE_PAYMENT_PROCESSED = "PAYMENT_PROCESSED";
    private static final String EVENT_TYPE_PAYMENT_FAILED = "PAYMENT_FAILED";

    @Override
    public Mono<Void> emitPaymentCreated(String paymentId, String transactionId, String channel, double amount) {
        return emitAuditEvent(EVENT_TYPE_PAYMENT_CREATED, paymentId, Map.of(
            "transactionId", transactionId,
            "channel", channel,
            "amount", String.valueOf(amount),
            "timestamp", Instant.now().toString()
        ));
    }

    @Override
    public Mono<Void> emitPaymentProcessed(String paymentId, String status, String externalReference) {
        return emitAuditEvent(EVENT_TYPE_PAYMENT_PROCESSED, paymentId, Map.of(
            "status", status,
            "externalReference", externalReference != null ? externalReference : "N/A",
            "timestamp", Instant.now().toString()
        ));
    }

    @Override
    public Mono<Void> emitPaymentFailed(String paymentId, String errorCode, String errorMessage) {
        return emitAuditEvent(EVENT_TYPE_PAYMENT_FAILED, paymentId, Map.of(
            "errorCode", errorCode,
            "errorMessage", errorMessage,
            "timestamp", Instant.now().toString()
        ));
    }

    private Mono<Void> emitAuditEvent(String eventType, String paymentId, Map<String, String> metadata) {
        String eventId = UUID.randomUUID().toString();
        
        return Mono.fromRunnable(() -> {
            log.info("Audit Event [{}] - Type: {}, PaymentId: {}, Metadata: {}",
                eventId, eventType, paymentId, metadata);
        }).then();
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/config/Resilience4jConfig.java ===
package com.pragma.payment.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofSeconds(30))
            .slidingWindowSize(10)
            .minimumNumberOfCalls(5)
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();
        
        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(500))
            .build();
        
        return RetryRegistry.of(config);
    }

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
            .limitRefreshPeriod(Duration.ofSeconds(1))
            .limitForPeriod(10)
            .timeoutDuration(Duration.ofMillis(500))
            .build();
        
        return RateLimiterRegistry.of(config);
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/controller/PaymentController.java ===
package com.pragma.payment.infrastructure.controller;

import com.pragma.payment.application.PaymentService;
import com.pragma.payment.domain.exception.DuplicatePaymentException;
import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Mono<ResponseEntity<PaymentResponse>> processPayment(
            @Valid @RequestBody PaymentRequestDTO request) {
        
        log.info("Received payment request - Channel: {}, TransactionId: {}, Amount: {}",
            request.channel(), request.transactionId(), request.amount());
        
        PaymentRequest paymentRequest = new PaymentRequest(
            request.transactionId(),
            request.channel(),
            request.amount(),
            request.idempotencyKey()
        );
        
        return paymentService.processPayment(paymentRequest)
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
            .onErrorResume(DuplicatePaymentException.class, ex -> {
                log.warn("Duplicate payment detected - IdempotencyKey: {}, ExistingPaymentId: {}",
                    ex.getIdempotencyKey(), ex.getExistingPaymentId());
                return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(PaymentResponse.duplicate(ex.getExistingPaymentId())));
            })
            .onErrorResume(Exception.class, ex -> {
                log.error("Payment processing failed - TransactionId: {}, Error: {}",
                    request.transactionId(), ex.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(PaymentResponse.failure(null, ex.getMessage())));
            });
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "payment-service"));
    }

    public record PaymentRequestDTO(
        @NotBlank(message = "Transaction ID is required")
        String transactionId,
        
        @NotBlank(message = "Channel is required")
        String channel,
        
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        Double amount,
        
        @NotBlank(message = "Idempotency key is required")
        String idempotencyKey
    ) {}
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/repository/entity/PaymentEntity.java ===
package com.pragma.payment.infrastructure.repository.entity;


import com.pragma.payment.domain.model.PaymentStatus;
import com.pragma.payment.domain.model.PaymentRequest;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_idempotency_key", columnList = "idempotencyKey", unique = true),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "createdAt")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "transaction_id", nullable = false, length = 64)
    private String transactionId;

    @Column(name = "channel", nullable = false, length = 32)
    private String channel;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private Double amount;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 128)
    private String idempotencyKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private PaymentRequest.PaymentStatus status;

    @Column(name = "error_message", length = 512)
    private String errorMessage;

    @Column(name = "external_service_id", length = 128)
    private String externalServiceId;

    @Column(name = "retry_count", nullable = false)
    @Builder.Default
    private Integer retryCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public static PaymentEntity fromDomain(com.pragma.payment.domain.model.PaymentRequest request) {
        return PaymentEntity.builder()
                .transactionId(request.transactionId())
                .channel(request.channel())
                .amount(request.amount())
                .idempotencyKey(request.idempotencyKey())
                .status(request.status())
                .retryCount(0)
                .build();
    }

    public com.pragma.payment.domain.model.PaymentRequest toDomain() {
        return new com.pragma.payment.domain.model.PaymentRequest(
                this.transactionId,
                this.channel,
                this.amount,
                this.idempotencyKey,
                this.status,
                this.errorMessage,
                this.externalServiceId,
                this.retryCount
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/payment/infrastructure/config/GlobalExceptionHandler.java ===
package com.pragma.payment.infrastructure.config;

import com.pragma.payment.domain.exception.DuplicatePaymentException;
import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.NetworkException;
import com.pragma.payment.domain.model.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatePaymentException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleDuplicatePayment(DuplicatePaymentException ex) {
        log.warn("Pago duplicado detectado - idempotencyKey: {}, paymentId existente: {}",
                ex.getIdempotencyKey(), ex.getExistingPaymentId());

        PaymentResponse response = PaymentResponse.duplicate(ex.getExistingPaymentId());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
    }

    @ExceptionHandler(ExternalServiceException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleExternalServiceException(ExternalServiceException ex) {
        log.error("Error del servicio externo: {}", ex.getMessage(), ex);

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Error en el servicio externo: " + ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response));
    }

    @ExceptionHandler(NetworkException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleNetworkException(NetworkException ex) {
        log.error("Error de red: {}", ex.getMessage(), ex);

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Error de conexión: " + ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Argumento inválido: {}", ex.getMessage());

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Solicitud inválida: " + ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Mono<ResponseEntity<PaymentResponse>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("Tipo de argumento inválido: {}", ex.getMessage());

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Tipo de parámetro inválido"
        );
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<PaymentResponse>> handleGenericException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);

        PaymentResponse response = PaymentResponse.failure(
                UUID.randomUUID().toString(),
                "Error interno del servidor"
        );
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
    }
}

// === ARCHIVO: src/test/java/com/pragma/payment/application/PaymentServiceTest.java ===
package com.pragma.payment.application;


import com.pragma.payment.domain.model.PaymentStatus;
import com.pragma.payment.domain.exception.DuplicatePaymentException;
import com.pragma.payment.domain.exception.ExternalServiceException;
import com.pragma.payment.domain.exception.NetworkException;
import com.pragma.payment.domain.model.PaymentRequest;
import com.pragma.payment.domain.model.PaymentResponse;
import com.pragma.payment.domain.port.PaymentPersistencePort;
import com.pragma.payment.domain.port.ExternalServicePort;
import com.pragma.payment.domain.port.AuditEventPort;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentPersistencePort paymentPersistencePort;

    @Mock
    private ExternalServicePort externalServicePort;

    @Mock
    private AuditEventPort auditEventPort;

    @Mock
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Mock
    private CircuitBreaker circuitBreaker;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
        paymentService = new PaymentService(
                paymentPersistencePort,
                externalServicePort,
                auditEventPort,
                circuitBreakerRegistry
        );
    }

    @Test
    void processPayment_success_returnsApprovedResponse() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest request = new PaymentRequest(
                "txn-" + System.currentTimeMillis(),
                "WEB",
                1000.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );

        when(paymentPersistencePort.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.empty());
        when(paymentPersistencePort.save(any(PaymentRequest.class)))
                .thenAnswer(inv -> Mono.just(request));
        when(externalServicePort.verifyFunds(any(PaymentRequest.class)))
                .thenReturn(Mono.just("approval-" + System.currentTimeMillis()));
        when(auditEventPort.publish(any(PaymentRequest.class)))
                .thenReturn(Mono.empty());

        // When
        Mono<PaymentResponse> result = paymentService.processPayment(request);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(response -> "approval-".equals(response.paymentId().substring(0, 8)))
                .verifyComplete();
    }

    @Test
    void processPayment_duplicateIdempotencyKey_throwsDuplicatePaymentException() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest existingRequest = new PaymentRequest(
                "txn-existing",
                "MOBILE",
                500.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.APPROVED,
                null,
                "existing-payment-id",
                0
        );

        when(paymentPersistencePort.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.just(existingRequest));

        // When
        PaymentRequest newRequest = new PaymentRequest(
                "txn-new",
                "MOBILE",
                500.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );
        Mono<PaymentResponse> result = paymentService.processPayment(newRequest);

        // Then
        StepVerifier.create(result)
                .expectError(DuplicatePaymentException.class)
                .verify();
    }

    @Test
    void processPayment_externalServiceFailure_throwsExternalServiceException() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest request = new PaymentRequest(
                "txn-" + System.currentTimeMillis(),
                "API",
                2500.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );

        when(paymentPersistencePort.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.empty());
        when(paymentPersistencePort.save(any(PaymentRequest.class)))
                .thenAnswer(inv -> Mono.just(request));
        when(externalServicePort.verifyFunds(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new ExternalServiceException("Fondos insuficientes")));

        // When
        Mono<PaymentResponse> result = paymentService.processPayment(request);

        // Then
        StepVerifier.create(result)
                .expectError(ExternalServiceException.class)
                .verify();
    }

    @Test
    void processPayment_networkError_retriesAndEventuallyFails() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest request = new PaymentRequest(
                "txn-" + System.currentTimeMillis(),
                "WEB",
                100.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );

        when(paymentPersistencePort.findByIdempotencyKey(idempotencyKey))
                .thenReturn(Mono.empty());
        when(paymentPersistencePort.save(any(PaymentRequest.class)))
                .thenAnswer(inv -> Mono.just(request));
        when(externalServicePort.verifyFunds(any(PaymentRequest.class)))
                .thenReturn(Mono.error(new NetworkException("Connection timeout")));

        // When
        Mono<PaymentResponse> result = paymentService.processPayment(request);

        // Then
        StepVerifier.create(result)
                .expectError(NetworkException.class)
                .verify();
    }

    @Test
    void processPayment_invalidAmount_throwsIllegalArgumentException() {
        // Given
        String idempotencyKey = UUID.randomUUID().toString();
        PaymentRequest request = new PaymentRequest(
                "txn-" + System.currentTimeMillis(),
                "WEB",
                -100.00,
                idempotencyKey,
                PaymentRequest.PaymentStatus.PENDING,
                null,
                null,
                0
        );

        // When
        Mono<PaymentResponse> result = paymentService.processPayment(request);

        // Then
        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();
    }
}
```
