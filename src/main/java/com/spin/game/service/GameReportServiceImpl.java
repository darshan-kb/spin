package com.spin.game.service;

import com.spin.game.dto.GameRecordDTO;
import com.spin.game.dto.TicketReportDTO;
import com.spin.game.entities.*;
import com.spin.game.repository.ClaimBetRepository;
import com.spin.game.repository.GameRepo;
import com.spin.game.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameReportServiceImpl implements GameReportService{
    private final GameRepo gameRepo;
    private final TicketRepository ticketRepository;
    private final ClaimBetRepository claimBetRepository;

    @Override
    public List<Game> lastNGames(int n) {
        return gameRepo.findAll(PageRequest.of(0,n, Sort.by("gameTimeStamp").descending())).toList();
    }


    @Override
    @Transactional
    public List<TicketReportDTO> getTickets(int page, String email) {
        List<Ticket> tickets = ticketRepository.findAllByUsername(email,PageRequest.of(page,10,Sort.by("timestamp").descending()));
        List<TicketReportDTO> ticketReportDTOS = new ArrayList<>();
        for(Ticket t : tickets){
            for(Bet bet : t.getBets()){
                Optional<ClaimBet> claimBet = claimBetRepository.findByBet(bet);
                ticketReportDTOS.add(
                        TicketReportDTO.builder()
                                .ticketId(t.getTicketId())
                                .timestamp(t.getTimestamp())
                                .betId(bet.getBetId())
                                .betAmount(bet.getAmount())
                                .betName(bet.getBetName())
                                .amountWon(claimBet.map(ClaimBet::getAmount).orElse(0.0))
                                .build()
                );
            }
        }
        return ticketReportDTOS;
    }

    @Override
    public long totalTickets(String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return ticketRepository.countByUsername(email);
    }

    @Override
    public int lastGameResult() {
        return gameRepo.findLatestGame().getResultValue();
    }

    @Override
    public List<GameRecordDTO> lastTenGameRecord() {
        return gameRepo.findLastTenGame().stream().map((i) -> new GameRecordDTO(i.getGameTimeStamp(),i.getResultValue())).toList();
    }




}
