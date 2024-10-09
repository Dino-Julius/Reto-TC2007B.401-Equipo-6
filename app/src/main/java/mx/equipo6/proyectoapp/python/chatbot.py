import sys
import time
import time
from langchain_ollama import ChatOllama
from langchain_core.messages import HumanMessage, SystemMessage
from langchain_core.chat_history import InMemoryChatMessageHistory
from langchain_core.runnables.history import RunnableWithMessageHistory

#print("\n\n¡Hola! Soy el chatbot de Zazil que puede responder preguntas acerca de la menstruación para erradicar la desinformación menstrual.")

system_message = SystemMessage(content = """
Eres un chatbot de la empresa Zazil especializado en responder preguntas exclusivamente sobre la menstruación. Debes seguir las siguientes reglas:

1.  Saludo personalizado: Si el usuario se presenta diciendo "Hola soy [nombre]", debes responder:
    - ¡Hola [nombre]! Gusto en conocerte y bienvenido al chatbot de Zazil. ¿En qué puedo ayudarte hoy?
    - Sin embargo, si el usuario solo dice Hola: saludalo y ofrece tus conocimientos.

2.  Temas fuera de la menstruación: Si el usuario te pregunta sobre algún tema que no esté relacionado con la menstruación, responde:
    - Lo lamento, no puedo contestar ese tipo de preguntas.

3.  Preguntas sobre menstruación: Si el usuario pregunta algo relacionado con la menstruación, respondelas inmediatamente
    
4.  Solicitudes para hablar con un humano: Si el usuario pregunta si puede hablar con un humano, responde:
    - ¡Claro! Por favor contáctanos al 55 1234 5678 o escríbenos en nuestras redes sociales @zazil.
    - Puedo ayudarte con cualquier otra pregunta que tengas.

"""
                               )

model = ChatOllama(
    model="llama3.1",
    temperature=0,  # less randomness data
)

message_history = InMemoryChatMessageHistory()

with_message_history = RunnableWithMessageHistory(
    runnable=model,
    get_session_history=lambda: message_history
)

#print("\n\n¡Hola! Soy el chatbot de Zazil que puede responder preguntas acerca de la menstruación para erradicar la desinformación menstrual.")
#print("Escribe 'exit', 'quit' o 'salir' para terminar la conversación.")

while True:
    if len(sys.argv) < 2:
        print("Por favor, proporciona una pregunta como argumento.")
        break

    user_input = sys.argv[1]

    print("Chatbot: ", end="", flush=True)
    response_message = ""
    for r in with_message_history.stream(
            [
                system_message,
                HumanMessage(content=user_input)
            ]
    ):
        response_message += r.content
        print(f"{r.content}", end="", flush=True)
        # time.sleep(0.01)  # Simulate typing delay

    print()  # Move to the next line after the response is complete
    break  # Exit after processing the input once