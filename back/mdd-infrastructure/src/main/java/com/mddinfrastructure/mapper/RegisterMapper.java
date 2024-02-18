package mapper;

import com.openclassrooms.mddapi.core.usecases.EntityMapper;
import com.openclassrooms.mddapi.core.usecases.user.dto.UserDto;
import com.openclassrooms.mddapi.infrastructure.request.RegisterRequest;

public interface RegisterMapper extends EntityMapper<RegisterRequest, UserDto> {
}
