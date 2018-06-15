package com.mrbt.lingmoney.model;

public class WorldCupMatchGuessVo extends WorldCupMatchInfo {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 竞猜选择
     * 表字段 : world_cup_guessing.game_choice
     */
    private Integer gameChoice;
    
    /**
     * 队伍left支持率
     */
    private String leftSupportRate;
    
    /**
     * 兑付right支持率
     */
    private String rightSupportRate;

	public Integer getGameChoice() {
		return gameChoice;
	}

	public void setGameChoice(Integer gameChoice) {
		this.gameChoice = gameChoice;
	}

	public String getRightSupportRate() {
		return rightSupportRate;
	}

	public void setRightSupportRate(String rightSupportRate) {
		this.rightSupportRate = rightSupportRate;
	}

	public String getLeftSupportRate() {
		return leftSupportRate;
	}

	public void setLeftSupportRate(String leftSupportRate) {
		this.leftSupportRate = leftSupportRate;
	}
}
