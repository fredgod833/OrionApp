package com.mddcore.usecases.subscription;

import com.mddcore.domain.models.Subject;
import com.mddcore.domain.models.Subscription;
import com.mddcore.domain.models.User;
import com.mddcore.domain.repository.ISubjectRepository;
import com.mddcore.domain.repository.ISubscriptionRepository;
import com.mddcore.domain.repository.IUserRepository;
import com.mddcore.usecases.UseCase;

public class AddSubscriptionUseCase extends UseCase<AddSubscriptionUseCase.InputValues, AddSubscriptionUseCase.OutputValues> {

    private final ISubscriptionRepository repository;
    private final IUserRepository userRepository;
    private final ISubjectRepository subjectRepository;

    public AddSubscriptionUseCase(ISubscriptionRepository repository, IUserRepository userRepository, ISubjectRepository subjectRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        try {
            User user = userRepository.findById(input.userId()).orElse(null);
            Subject subject = subjectRepository.findById(input.subjectId()).orElse(null);

            if (subject == null || user == null) {
                throw new IllegalArgumentException("Subject or User can not be null");
            }

            Subscription subscription = Subscription.newInstance(
                    subject,
                    user
            );

            repository.save(subscription);

            return new OutputValues(true);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while adding subscription", e);
        }
    }

    public record InputValues(Long userId, Long subjectId) implements UseCase.InputValues {}

    public record OutputValues(Boolean isAdded) implements UseCase.OutputValues {}
}
