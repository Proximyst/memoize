//
// memoize - Memoize the return values of an expensive, dynamic state method.
// Copyright (C) 2021 Mariell Hoversholm
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.
//

package st.proximy.memoize;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Memoizable} for a {@code double}.
 */
public final class MemoizableDouble implements Memoizable<@NonNull Double> {
  private final @NonNull DoubleSupplier doubleSupplier;
  private boolean hasMemoized = false;
  private double memoizedValue;

  public MemoizableDouble(final @NonNull DoubleSupplier doubleSupplier) {
    this.doubleSupplier = doubleSupplier;
  }

  @SuppressWarnings("java:S4276")
  public MemoizableDouble(final @NonNull Supplier<@NonNull Double> doubleSupplier) {
    this.doubleSupplier = doubleSupplier::get;
  }

  public MemoizableDouble(final double memoizedValue) {
    this.doubleSupplier = () -> memoizedValue;
    this.hasMemoized = true;
    this.memoizedValue = memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #evaluateDataAsDouble()
   */
  @Override
  public @NonNull Double evaluateData() {
    return this.evaluateDataAsDouble();
  }

  /**
   * Evaluates the data like {@link #evaluateData()}, except with a primitive. See that method for more detail.
   *
   * @return the now memoized value.
   * @see #evaluateData()
   */
  public double evaluateDataAsDouble() {
    this.hasMemoized = true;
    this.memoizedValue = this.doubleSupplier.getAsDouble();
    return this.memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #memoizedAsDouble()
   */
  @Override
  public @NonNull Double memoized() {
    return this.memoizedAsDouble();
  }

  /**
   * Returns the memoized value, or evaluates one then returns it if there is none currently memoized, just like in
   * {@link #memoized()} except with a primitive. See {@link #memoized()} for more detail.
   *
   * @return the now memoized value.
   * @see #memoized()
   */
  public double memoizedAsDouble() {
    if (!this.hasMemoized) {
      return this.evaluateData();
    }

    return this.memoizedValue;
  }

  /**
   * Get whether there is currently a memoized value within this.
   *
   * @return whether there is a memoized value.
   */
  public boolean hasMemoized() {
    return this.hasMemoized;
  }
}
