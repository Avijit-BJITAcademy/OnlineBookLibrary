package miniproject.onlinebookstore.service;

import miniproject.onlinebookstore.dto.HistoryResponse;
import miniproject.onlinebookstore.dto.UserDto;
import miniproject.onlinebookstore.entity.Role;
import miniproject.onlinebookstore.entity.User;
import miniproject.onlinebookstore.entity.UserHistory;
import miniproject.onlinebookstore.exception.IdNotFoundException;
import miniproject.onlinebookstore.repository.UserHistoryRepository;
import miniproject.onlinebookstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    private final UserHistoryRepository userHistoryRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository repository, UserHistoryRepository userHistoryRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.userHistoryRepository = userHistoryRepository;
        this.modelMapper = modelMapper;
    }

    public UserDto createUser (User user){
        user.setRole(Role.CUSTOMER);
        User createdUser = repository.save(user);
        return modelMapper.map(createdUser, UserDto.class);
    }

    public UserDto getUser (Long userId) throws IdNotFoundException {
        return modelMapper.map(
                repository.findById(userId).orElseThrow(()
                        -> new IdNotFoundException("User not found with given ID"))
                , UserDto.class
        );
    }


    public List<HistoryResponse> getPreviouslyBorrowedBooksByUser(Long userId) {
        List<HistoryResponse> historyResponses = new ArrayList<>();
        for (UserHistory userHistory : userHistoryRepository.findPreviouslyBorrowedByUserId(userId)){
            HistoryResponse historyResponse = modelMapper.map(userHistory, HistoryResponse.class);
            historyResponse.setUserDto(modelMapper.map(userHistory.getUser(), UserDto.class));
            historyResponses.add(historyResponse);
        }
        return historyResponses;
    }

    public List<HistoryResponse> getCurrentlyBorrowedBooksByUser(Long userId) {
        List<HistoryResponse> historyResponses = new ArrayList<>();
        for (UserHistory userHistory : userHistoryRepository.findCurrentlyBorrowedByUserId(userId)){
            HistoryResponse historyResponse = modelMapper.map(userHistory, HistoryResponse.class);
            historyResponse.setUserDto(modelMapper.map(userHistory.getUser(), UserDto.class));
            historyResponses.add(historyResponse);
        }
        return historyResponses;
    }
}
