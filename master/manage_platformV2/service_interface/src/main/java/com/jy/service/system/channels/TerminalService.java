package com.jy.service.system.channels;

import com.jy.entity.system.channels.Merchant;
import com.jy.entity.system.channels.Prepayment;
import com.jy.entity.system.channels.PrepaymentExtend;
import com.jy.from.system.request.OutLineDataForm;
import com.jy.from.system.request.TerminalForm;

import java.util.List;
import java.util.Map;

/**
 * Created by ZQY on 2017/5/17.
 */
public interface TerminalService {
    public void addTerminal(TerminalForm terminalForm) throws Exception;
    public void delTerminal(TerminalForm terminalForm) throws Exception;
    public void updateTerminal(TerminalForm terminalForm) throws Exception;
    public List<TerminalForm> findTerminal(Map map) throws  Exception;
    public TerminalForm findTerminals(Map map) throws  Exception;
    public List<TerminalForm> findTerminal(Map map,int a) throws  Exception;

}
