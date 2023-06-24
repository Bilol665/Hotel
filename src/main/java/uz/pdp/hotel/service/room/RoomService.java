package uz.pdp.hotel.service.room;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.hotel.entity.dto.RoomCreateDto;
import uz.pdp.hotel.entity.room.RoomEntity;
import uz.pdp.hotel.exceptions.DataNotFoundException;
import uz.pdp.hotel.exceptions.RequestValidationException;
import uz.pdp.hotel.repository.room.RoomRepository;
import uz.pdp.hotel.repository.room.RoomStatusRepository;
import uz.pdp.hotel.repository.room.RoomTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomStatusRepository roomStatusRepository;
    private final ModelMapper modelMapper;
    public RoomEntity save(RoomCreateDto roomCreateDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        RoomEntity roomEntity = modelMapper.map(roomCreateDto, RoomEntity.class);
        roomEntity.setType(roomTypeRepository.findRoomTypeByType(roomCreateDto.getType().toUpperCase()).orElseThrow(
                () -> new DataNotFoundException("Room type not found!")
        ));
        if(roomCreateDto.getStatus() == null)
            roomEntity.setStatus(roomStatusRepository.findRoomStatusByStatus("EMPTY").orElseThrow(
                    () -> new DataNotFoundException("Status not found!")
            ));
        else
            roomEntity.setStatus(roomStatusRepository.findRoomStatusByStatus(roomCreateDto.getStatus().toUpperCase()).orElseThrow(
                    () -> new DataNotFoundException("Status not found!")
            ));
        if(roomCreateDto.getHasMonitor() == null)
            roomEntity.setHasMonitor(false);
        if(roomCreateDto.getSize() == null)
            roomEntity.setSize(2);
        return roomRepository.save(roomEntity);
    }
}
