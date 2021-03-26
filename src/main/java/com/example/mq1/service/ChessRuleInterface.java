package com.example.mq1.service;

import com.example.mq1.bean.CheckerBoard;
import com.example.mq1.bean.Chess;
import com.example.mq1.bean.RuleResult;

public interface ChessRuleInterface {
    public RuleResult rule(CheckerBoard board, Chess chess);
}
