public final class Runner {
        public String run(Provider provider, int limit) {
            // TODO: validate 1..6, count all calls, enforce READ -> LIST -> PROPOSE.
            // Provider exceptions yield PROVIDER_ERROR; exhaustion yields STEP_LIMIT.
            if (limit < 1 || limit > 6) {
                throw new IllegalArgumentException("Number mut be in between 1-6");
            }
            if (provider == null) {
                throw new IllegalArgumentException("Provider cannot be null.");
            }

            for (int calls = 0;call < limit; calls++) {                
                string command;
                try {
                    command = provider.next();
                }
                catch (Exception e) {
                    return "PROVIDER_ERROR";
                }
            }

            return calls;
            
            
            throw new UnsupportedOperationException("TODO Lab04 Runner.run");
        }
    }