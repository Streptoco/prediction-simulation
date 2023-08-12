package engine.value.generator;

public interface ValueGeneratorFactory {
    static <T> ValueGenerator<T>createFixed(T value) {
        return new FixedValueGenerator<T>(value);
    }

}
