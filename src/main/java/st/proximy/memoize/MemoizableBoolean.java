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

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Memoizable} for a {@code boolean}.
 */
public final class MemoizableBoolean implements Memoizable<@NonNull Boolean> {
  private final @NonNull BooleanSupplier booleanSupplier;
  private boolean hasMemoized = false;
  private boolean memoizedValue;

  public MemoizableBoolean(final @NonNull BooleanSupplier booleanSupplier) {
    this.booleanSupplier = booleanSupplier;
  }

  @SuppressWarnings("java:S4276")
  public MemoizableBoolean(final @NonNull Supplier<@NonNull Boolean> booleanSupplier) {
    this.booleanSupplier = booleanSupplier::get;
  }

  public MemoizableBoolean(final boolean memoizedValue) {
    this.booleanSupplier = () -> memoizedValue;
    this.hasMemoized = true;
    this.memoizedValue = memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #evaluateDataAsBoolean()
   */
  @Override
  public @NonNull Boolean evaluateData() {
    return this.evaluateDataAsBoolean();
  }

  /**
   * Evaluates the data like {@link #evaluateData()}, except with a primitive. See that method for more detail.
   *
   * @return the now memoized value.
   * @see #evaluateData()
   */
  public boolean evaluateDataAsBoolean() {
    this.hasMemoized = true;
    this.memoizedValue = this.booleanSupplier.getAsBoolean();
    return this.memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #memoizedAsBoolean()
   */
  @Override
  public @NonNull Boolean memoized() {
    return this.memoizedAsBoolean();
  }

  /**
   * Returns the memoized value, or evaluates one then returns it if there is none currently memoized, just like in
   * {@link #memoized()} except with a primitive. See {@link #memoized()} for more detail.
   *
   * @return the now memoized value.
   * @see #memoized()
   */
  public boolean memoizedAsBoolean() {
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
