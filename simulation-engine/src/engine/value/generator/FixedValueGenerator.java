package engine.value.generator;

public class FixedValueGenerator<T> implements ValueGenerator {
    private final T fixedValue;

    public FixedValueGenerator(T fixedValue) {
        this.fixedValue = fixedValue;
    }

    @Override
    public T generateValue() {
        return fixedValue;
    }
}
