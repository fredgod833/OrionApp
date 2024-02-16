package com.openclassrooms.p6.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.p6.exception.ApiException;
import com.openclassrooms.p6.exception.GlobalExceptionHandler;
import com.openclassrooms.p6.mapper.ThemeMapper;
import com.openclassrooms.p6.model.Subscriptions;
import com.openclassrooms.p6.model.Themes;
import com.openclassrooms.p6.model.Users;
import com.openclassrooms.p6.payload.request.ArticleRequest;
import com.openclassrooms.p6.payload.response.MultipleArticlesResponse;
import com.openclassrooms.p6.payload.response.MultipleThemesResponse;
import com.openclassrooms.p6.payload.response.SingleThemeResponse;
import com.openclassrooms.p6.service.SubscriptionsService;
import com.openclassrooms.p6.service.ThemeService;
import com.openclassrooms.p6.service.UserService;
import com.openclassrooms.p6.utils.JwtUtil;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/themes")
public class ThemesController {

    // TODO create the remaining endpoint methods:
    // TODO: create the subscription mapper
    // TODO: create methods to subscribe/unsubscribe

    @Autowired
    private ThemeService themeService;

    @Autowired
    private SubscriptionsService subscriptionsService;

    @Autowired
    private ThemeMapper themeMapper;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllThemes(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            Long userId = verifyUserValidityFromToken(authorizationHeader);

            Iterable<Subscriptions> subscriptions = subscriptionsService.findAllUserSubscriptions(userId);

            List<Themes> themesEntityList = themeService.getThemes();

            Iterable<SingleThemeResponse> themesDto = (themeMapper.toDtoThemes(themesEntityList));

            List<SingleThemeResponse> normalizedThemes = new ArrayList<>();

            for (SingleThemeResponse themeDto : themesDto) {
                boolean isSubscribed = false;

                for (Subscriptions subscription : subscriptions) {
                    Boolean userHasSubcriptionInfoForTheme = subscription.getThemeId().equals(themeDto.id());
                    if (userHasSubcriptionInfoForTheme) {
                        isSubscribed = subscription.getIsSubscribed();
                        break;
                    }
                }

                SingleThemeResponse normalizedSingleResponse = new SingleThemeResponse(themeDto.id(), themeDto.title(),
                        themeDto.description(), isSubscribed);

                normalizedThemes.add(normalizedSingleResponse);
            }

            MultipleThemesResponse response = new MultipleThemesResponse(normalizedThemes);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    @GetMapping("/subscribed")
    public ResponseEntity<?> getSubscribedThemes(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            Long userId = verifyUserValidityFromToken(authorizationHeader);

            Iterable<Subscriptions> subscriptions = subscriptionsService.findAllUserSubscriptions(userId);

            List<Themes> themesEntityList = themeService.getThemes();

            Iterable<SingleThemeResponse> themesDto = (themeMapper.toDtoThemes(themesEntityList));

            List<SingleThemeResponse> normalizedThemes = new ArrayList<>();

            for (SingleThemeResponse themeDto : themesDto) {
                boolean isSubscribed = false;

                for (Subscriptions subscription : subscriptions) {
                    Boolean userHasSubcriptionInfoForTheme = subscription.getThemeId().equals(themeDto.id());
                    if (userHasSubcriptionInfoForTheme) {
                        isSubscribed = subscription.getIsSubscribed();
                        break;
                    }
                }

                if (isSubscribed) {
                    SingleThemeResponse normalizedSingleResponse = new SingleThemeResponse(themeDto.id(),
                            themeDto.title(),
                            themeDto.description(), isSubscribed);

                    normalizedThemes.add(normalizedSingleResponse);
                }
            }

            MultipleThemesResponse response = new MultipleThemesResponse(normalizedThemes);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ApiException e) {
            return GlobalExceptionHandler.handleApiException(e);
        }
    }

    // @PostMapping("/subscribe")
    // public ResponseEntity<?> postArticle(@Valid @RequestBody Subsc request,
    // BindingResult bindingResult,
    // @RequestHeader("Authorization") String authorizationHeader) {}

    // @PostMapping("/unsubscribe")
    // public ResponseEntity<?> postArticle(@Valid @RequestBody Subsc request,
    // BindingResult bindingResult,
    // @RequestHeader("Authorization") String authorizationHeader) {}

    /**
     * Retrieves a user by their ID and verifies their existence.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the given ID.
     * @throws ApiException if the user with the given ID does not exist.
     */
    private Users getVerifiedUserById(Long userId) {
        Optional<Users> optionalSpecificUser = userService.getUserById(userId);

        Boolean userWithIdDoesNotExist = optionalSpecificUser.isEmpty();
        if (userWithIdDoesNotExist) {
            GlobalExceptionHandler.handleLogicError("Not found",
                    HttpStatus.NOT_FOUND);
        }

        return optionalSpecificUser.get();
    }

    /**
     * Retrieves a user by their ID and verifies their existence.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the given ID.
     * @throws ApiException if the user with the given ID does not exist.
     */
    private Long verifyUserValidityFromToken(String authorizationHeader) {
        String jwtToken = JwtUtil.extractJwtFromHeader(authorizationHeader);

        // Extract user ID from JWT
        Optional<Long> optionalUserIdFromToken = JwtUtil.extractUserId(jwtToken);

        Boolean hasJwtExtractionError = optionalUserIdFromToken.isEmpty();
        if (hasJwtExtractionError) {
            GlobalExceptionHandler.handleLogicError("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Long userId = optionalUserIdFromToken.get();

        getVerifiedUserById(userId);

        return userId;
    }

}
