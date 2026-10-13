# Implementación de Manejo de Errores y Reintentos en un Sistema de Procesamiento de Pagos

En un sistema de procesamiento de pagos de una entidad financiera, el servicio de pago debe manejar diferentes tipos de errores y realizar reintentos automáticos para asegurar la correcta ejecución de las transacciones. Los pagos provienen de tres canales distintos: web, móvil y API de terceros. El sistema debe persistir cada solicitud de pago con una clave de idempotencia y manejar adecuadamente los errores de red, timeouts y respuestas inválidas del servicio externo que verifica la disponibilidad de fondos.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | E2E prod technologiesWork 202610131810 |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Configuración del Canal de Entrada

**Objetivo:** Establecer un canal de entrada operativo que persista las solicitudes de pago con idempotencia.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Diseña un mecanismo para aceptar solicitudes de pago desde los tres canales declarados.
- Implementa la persistencia de cada solicitud con una clave de idempotencia.
- Asegúrate de que el sistema devuelva la misma respuesta ante reintentos con la misma clave dentro de 24 horas.

**Entregable:** Servicio que acepta y persiste solicitudes de pago con idempotencia.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo diferenciar las solicitudes duplicadas.
- Piensa en la estructura de datos necesaria para almacenar la información de las solicitudes.

</details>

### Fase 2: Consolidación de Canales y Manejo de Errores

**Objetivo:** Consolidar los tres canales de entrada sin pérdida de datos ante backpressure de uno de ellos y manejar adecuadamente los errores.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Integra los tres canales de entrada en un único punto de procesamiento.
- Implementa la lógica para manejar errores de red, timeouts y respuestas inválidas del servicio externo.
- Asegúrate de que el sistema no pierda solicitudes ante backpressure de uno de los canales.

**Entregable:** Sistema que consolida los tres canales de entrada y maneja errores de forma efectiva.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo distribuir la carga entre los canales.
- Piensa en estrategias para manejar diferentes tipos de errores.

</details>

### Fase 3: Recuperación Automática y Emisión de Eventos

**Objetivo:** Implementar la recuperación automática ante caída del consumidor downstream y emitir evento al sistema de auditoría por cada aceptación.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Diseña un mecanismo de recuperación automática para reintentar las solicitudes fallidas ante caída del consumidor downstream.
- Implementa la emisión de un evento al sistema de auditoría por cada aceptación de pago.
- Asegúrate de que el sistema no reprocese las solicitudes ya emitidas.

**Entregable:** Sistema con recuperación automática y emisión de eventos de auditoría.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la frecuencia y el número máximo de reintentos.
- Piensa en cómo asegurar la idempotencia en la emisión de eventos.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la idempotencia en el contexto de este reto y cómo se aplica?
- **paraQueSirve**: ¿Para qué sirve manejar errores y reintentos en un sistema de procesamiento de pagos?
- **comoSeUsa**: ¿Cómo se usa la clave de idempotencia para evitar duplicados en las solicitudes de pago?
- **erroresComunes**: ¿Cuáles son los errores comunes que puede encontrar el sistema al procesar pagos y cómo se manejan?
- **queDecisionesImplica**: ¿Qué decisiones implica el diseño del mecanismo de recuperación automática ante caída del consumidor downstream?

## Criterios de Evaluacion

- Implementación de un canal de entrada que persista solicitudes de pago con idempotencia.
- Consolidación efectiva de los tres canales de entrada con manejo de errores.
- Recuperación automática ante caída del consumidor downstream y emisión de eventos de auditoría.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
