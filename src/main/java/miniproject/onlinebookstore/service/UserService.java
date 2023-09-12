package miniproject.onlinebookstore.service;

import miniproject.onlinebookstore.dto.UserDto;
import miniproject.onlinebookstore.entity.Role;
import miniproject.onlinebookstore.entity.User;
import miniproject.onlinebookstore.exception.IdNotFoundException;
import miniproject.onlinebookstore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
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


}
