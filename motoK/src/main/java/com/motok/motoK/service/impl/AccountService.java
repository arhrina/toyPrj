package com.motok.motoK.service.impl;

import com.motok.motoK.domain.entity.AccountEntity;
import com.motok.motoK.domain.vo.DailyAccountVO;
import com.motok.motoK.repository.AccountRepository;
import com.motok.motoK.service.AbAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService extends AbAccountService {

    private final AccountRepository accountRepository;

    public List<DailyAccountVO> findList() {
        List<AccountEntity> all = accountRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        return all.stream().map(p -> modelMapper.map(p, DailyAccountVO.class)).collect(Collectors.toList());
    }

    @Override
    public String 일일입력(DailyAccountVO vo) {
        ModelMapper modelMapper = new ModelMapper();
        AccountEntity entity = new AccountEntity();
        modelMapper.map(vo, entity);
        if(accountRepository.findByCreatedDate(entity) != null) {
            accountRepository.save(entity);
            return "success";
        }
        else {
            return "error";
        }
    }

    @Override
    public void 통계계산() {

    }
}
