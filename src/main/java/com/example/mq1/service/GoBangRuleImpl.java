package com.example.mq1.service;

import com.example.mq1.bean.CheckerBoard;
import com.example.mq1.bean.Chess;
import com.example.mq1.bean.RuleResult;

public class GoBangRuleImpl implements ChessRuleInterface {

    @Override
    public RuleResult rule(CheckerBoard board, Chess chess) {
        if (board.getBoard()[chess.getLocationX()][chess.getLocationY()] != null){
            return new RuleResult(false, "此地已被占领！");
        }
        return null;
    }
}
