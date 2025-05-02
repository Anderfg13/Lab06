package domain;

/**
 * Clase abstracta que representa un agente en la simulación de la ciudad.
 * Un agente tiene un estado emocional (feliz, indiferente, insatisfecho) y un contador de pasos.
 * Esta clase es la base para otros agentes en la simulación.
 */
public abstract class Agent {
    // Constantes que representan los estados emocionales del agente
    public final static char HAPPY = 'h';       // Estado feliz
    public final static char INDIFFERENT = 'i'; // Estado indiferente
    public final static char DISSATISFIED = 'd';// Estado insatisfecho

    // Estado actual del agente
    protected char state;

    // Contador de pasos del agente
    private int steps;

    /**
     * Constructor de la clase Agent.
     * Inicializa el agente con el estado "insatisfecho" y un contador de pasos en 0.
     */
    public Agent() {
        state = DISSATISFIED;
        steps = 0;
    }

    /**
     * Incrementa el contador de pasos del agente.
     */
    protected void step() {
        steps++;
    }

    /**
     * Devuelve el número de pasos que ha dado el agente.
     *
     * @return El número de pasos del agente.
     */
    public final int getSteps() {
        return steps;
    }

    /**
     * Indica si el objeto es un agente.
     * En esta implementación, siempre devuelve true.
     *
     * @return true, ya que este objeto es un agente.
     */
    public final boolean isAgent() {
        return true;
    }

    /**
     * Indica si el agente está feliz.
     *
     * @return true si el agente está feliz, false en caso contrario.
     */
    public final boolean isHappy() {
        return (state == Agent.HAPPY);
    }

    /**
     * Indica si el agente está indiferente.
     *
     * @return true si el agente está indiferente, false en caso contrario.
     */
    public final boolean isIndifferent() {
        return (state == Agent.INDIFFERENT);
    }

    /**
     * Indica si el agente está insatisfecho.
     *
     * @return true si el agente está insatisfecho, false en caso contrario.
     */
    public final boolean isDissatisfied() {
        return (state == Agent.DISSATISFIED);
    }

    /**
     * Establece el estado emocional del agente.
     *
     * @param newState El nuevo estado del agente (HAPPY, INDIFFERENT o DISSATISFIED).
     */
    public void setState(char newState) {
        this.state = newState;
    }
}