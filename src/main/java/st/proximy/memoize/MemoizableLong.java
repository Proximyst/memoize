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

import java.util.function.LongSupplier;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Memoizable} for a {@code long}.
 */
public final class MemoizableLong implements Memoizable<@NonNull Long> {
  private final @NonNull LongSupplier longSupplier;
  private boolean hasMemoized = false;
  private long memoizedValue;

  public MemoizableLong(final @NonNull LongSupplier longSupplier) {
    this.longSupplier = longSupplier;
  }

  @SuppressWarnings("java:S4276")
  public MemoizableLong(final @NonNull Supplier<@NonNull Long> longSupplier) {
    this.longSupplier = longSupplier::get;
  }

  public MemoizableLong(final long memoizedValue) {
    this.longSupplier = () -> memoizedValue;
    this.hasMemoized = true;
    this.memoizedValue = memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #evaluateDataAsLong()
   */
  @Override
  public @NonNull Long evaluateData() {
    return this.evaluateDataAsLong();
  }

  /**
   * Evaluates the data like {@link #evaluateData()}, except with a primitive. See that method for more detail.
   *
   * @return the now memoized value.
   * @see #evaluateData()
   */
  public long evaluateDataAsLong() {
    this.hasMemoized = true;
    this.memoizedValue = this.longSupplier.getAsLong();
    return this.memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #memoizedAsLong()
   */
  @Override
  public @NonNull Long memoized() {
    return this.memoizedAsLong();
  }

  /**
   * Returns the memoized value, or evaluates one then returns it if there is none currently memoized, just like in
   * {@link #memoized()} except with a primitive. See {@link #memoized()} for more detail.
   *
   * @return the now memoized value.
   * @see #memoized()
   */
  public long memoizedAsLong() {
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
