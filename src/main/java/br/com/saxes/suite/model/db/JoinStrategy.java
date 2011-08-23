package br.com.saxes.suite.model.db;

/**
 *
 * @author Felipe
 */
public enum JoinStrategy {

    INNER_JOIN("INNER JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    RIGHT_JOIN("RIGHT JOIN");

    private String joinCommand;

    JoinStrategy(String joinCommand) {
        this.joinCommand = joinCommand;
    }

    public String getJoinCommand() {
        return joinCommand;
    }

}
